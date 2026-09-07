# Analisi tecnica dettagliata — SIMULATORE_BIMESTRALE_LUCE.xlsx

Documento di analisi destinato a Manus.im per comprendere il funzionamento, la logica di calcolo e le criticità del simulatore di comparazione bollette luce (confronto tra due fornitori, con calcolo del risparmio bimestrale e annuale).

---

## 1. Scopo del file

Il file simula la bolletta bimestrale dell'energia elettrica di un **nuovo fornitore**, ricostruendola voce per voce (materia energia, trasporto, oneri di sistema, imposte, IVA), e la confronta con l'importo della bolletta del **fornitore precedente** (inserito a mano come valore fisso). Da questo confronto calcola:

- **Risparmio Bimestrale** = Importo bolletta vecchio fornitore − Totale fattura simulata nuovo fornitore
- **Risparmio Annuale** = Risparmio Bimestrale × 6 (6 bimestri = 12 mesi)

## 2. Struttura del workbook

Il file contiene **4 fogli**:

| Foglio | Contenuto |
|---|---|
| `DATI` | Foglio parametri (quasi vuoto, NON collegato agli altri fogli — vedi §7.1) |
| `NOVEMBRE-DICEMBRE` | Simulazione bimestre Nov+Dic (dettaglio Novembre + dettaglio Dicembre) |
| `DICEMBRE-GENNAIO` | Simulazione bimestre Dic+Gen (ma il dettaglio Dicembre è azzerato — vedi §7.3) |
| `GENNAIO-FEBBRAIO` | Simulazione bimestre (etichette interne incoerenti: "DETTAGLIO FEBBRAIO" e "DETTAGLIO MARZO" — vedi §7.4) |

Ogni foglio bimestrale ha la **stessa architettura**, ripetuta identica:

```
Righe 14–16   → Intestazione (Ragione Sociale, Data Riferimento, Totale Fattura = C29)
Righe 21–29   → QUADRO SINTETICO (aggregati + % sul totale fattura)
Righe 32–35   → IL TUO RISPARMIO (confronto con vecchio fornitore)
Righe 50–86   → DETTAGLIO MESE 1 (tabella voce per voce)
Righe 102–139 → DETTAGLIO MESE 2 (tabella voce per voce) + totali di bolletta
```

## 3. Struttura di ogni tabella di dettaglio mensile

Ogni riga di costo ha 5 colonne operative:

| Colonna | Significato |
|---|---|
| A | Descrizione della voce (es. "QUOTA ENERGIA ATTIVA F1") |
| B | Unità di misura (€/mese, €/kWh, €/kW, €/pod/mese) |
| C | **Corrispettivo** = prezzo unitario (input manuale) |
| D | **Quantità** = kWh consumati / kW impegnati / numero mesi (input manuale o formula) |
| E | IVA% (sempre 10 — colonna informativa, NON usata nei calcoli: l'IVA è applicata globalmente, vedi §5) |
| F | **Totale riga** = `C × D` |

### 3.1 Blocchi di ogni mese

**A) Spesa per la materia energia** (righe 52–68 mese 1 / 104–120 mese 2):
- Commercializzazione e vendita (quota fissa €/mese)
- DISPBT (componente dispacciamento BT)
- Quota energia attiva **F1, F2, F3** (le tre fasce orarie, con prezzo e kWh distinti per fascia)
- **Perdite di rete F1/F2/F3**: calcolate così:
  - prezzo: `C57 = C54` (stesso prezzo della fascia corrispondente)
  - quantità: `D57 = ROUND(D54*10/100; 0)` → **10% dei kWh della fascia, arrotondato all'intero**
- Componenti di dispacciamento (MSD, UES/Sicur., RTN/DIS, INT, SAL, mercato capacità, PCV variabile, oneri sbilanciamento, aggregazione misure): tutte moltiplicano il corrispettivo per i **kWh totali inclusi le perdite**. La quantità viene propagata a catena: `D60 = D54+D55+D56+D57+D58+D59` (somma kWh 3 fasce + 3 perdite), poi `D61 = D60`, `D62 = D61`, ecc.
- **Totale blocco**: `F69 = SUM(F52:F68)` (e `F121 = SUM(F104:F120)` per il mese 2)

**B) Trasporto e gestione contatore** (righe 71–73 / 123–125):
- Corrispettivo €/kW × potenza impegnata (3 kW nel file)
- Corrispettivo fisso €/pod/mese
- Corrispettivo €/kWh × **kWh senza perdite** (`D73 = D54+D55+D56`)
- Totale: `F74 = SUM(F71:F73)` / `F126 = SUM(F123:F125)`

**C) Oneri di sistema** (righe 76–78 / 128–130):
- Asos quota fissa (€/pod/mese)
- Arim quota variabile (€/kWh × kWh senza perdite)
- Asos quota variabile (€/kWh × kWh senza perdite)
- Totale: `F79 = SUM(F76:F78)` / `F131 = SUM(F128:F130)`

**D) Imposte** (riga 81 / 133):
- Accisa domestica 0,0227 €/kWh × kWh senza perdite
- Totale: `F82 = SUM(F81)` / `F134 = SUM(F133)`

## 4. Totali di bolletta (fondo del secondo mese)

| Cella | Formula | Significato |
|---|---|---|
| `F135` | valore manuale | Altre partite NON soggette a IVA (es. canone TV, conguagli; in GENNAIO-FEBBRAIO vale **−11,40 €**, un accredito) |
| `F138` | `=F134+F126+F131+F121+F82+F79+F74+F69(+F136)` | **TOTALE IMPONIBILE** = somma degli 8 subtotali dei due mesi |
| `F137` | `=F138*10/100` | **TOTALE IVA** = 10% flat sull'imponibile |
| `F139` | `=F122+F82+F74+F79+F69++F80127+F131+F126+F134+F137+F121+F135+K130` | **TOTALE FATTURA** = imponibile + IVA + partite non soggette a IVA |

⚠️ La formula di `F139` contiene riferimenti spuri (`F122`, `F80127`, `K130`, doppio `++`) che per fortuna puntano a celle vuote e quindi valgono 0, ma è fragile — vedi §7.2.

Esiste anche un `F86 = F69+F74+F79+F82+F84` a metà foglio ("TOTALE FATTURA"): è in realtà il **subtotale del solo primo mese senza IVA** (F84 è vuota). L'etichetta è fuorviante.

## 5. Gestione IVA

La colonna E (IVA% = 10) è **puramente descrittiva**. L'IVA non è calcolata riga per riga ma **una sola volta a livello di fattura**: `F137 = F138 × 10%`. Funziona perché tutte le voci hanno la stessa aliquota (10%, aliquota domestica). Se in futuro comparissero voci con IVA diversa (es. 22%), il modello andrebbe rivisto.

Le "altre partite" (F135) sono correttamente **escluse dall'imponibile IVA** e sommate solo nel totale fattura finale.

## 6. Quadro sintetico e calcolo del risparmio

Il quadro sintetico (righe 21–29) aggrega i subtotali dei due mesi:

| Cella | Formula | Voce |
|---|---|---|
| `C23` | `=F69+F121` | Totale materia prima (energia dei 2 mesi) |
| `C24` | `=F74+F79+F126+F131` | Trasporto contatore + oneri di sistema (2 mesi) |
| `C25` | `=F82+F134` | Imposte (2 mesi) |
| `C26` | `=F135` | Altre partite (canone TV / conguagli) |
| `C27` | `=F138` | Imponibile |
| `C28` | `=F137` | IVA |
| `C29` | `=F139` | **Totale fattura simulata** |
| `E23:E29` | `=Cn/$C$29` | Peso % di ogni voce sul totale fattura |

**Blocco risparmio (righe 32–35):**

| Cella | Formula / valore | Significato |
|---|---|---|
| `E33` | valore manuale (es. 137,06) | Importo bolletta bimestrale del **vecchio fornitore** |
| `E34` | `=E33-C29` | **Risparmio bimestrale** |
| `E35` | `=E34*6` | **Risparmio annuale** (6 bimestri) |

`E16` in intestazione riporta semplicemente `=C29`.

### Risultati attuali nel file

| Foglio | Fattura simulata (C29) | Bolletta vecchio fornitore (E33) | Risparmio bimestrale | Risparmio annuale |
|---|---|---|---|---|
| NOVEMBRE-DICEMBRE | 107,97 € | 137,06 € | 29,09 € | 174,51 € |
| DICEMBRE-GENNAIO | 44,28 € | 137,06 € | 92,78 € ⚠️ | 556,70 € ⚠️ |
| GENNAIO-FEBBRAIO | 59,11 € | 66,73 € | 7,62 € | 45,70 € |

## 7. Criticità e anomalie rilevate (importanti per chi deve intervenire)

### 7.1 Foglio DATI orfano
Contiene solo l'etichetta "Oneri generali sostegno rinnovabili (Asos) - quota fissa" in A3 e 4 valori senza etichetta in B4:B7 (7,6302 / 6,7709 / 0,3214 / 1,2554). **Nessun foglio lo referenzia**: i parametri (es. Asos 7,6302) sono ridigitati a mano nei fogli mensili. Il valore 7,6302 in B4 coincide con l'Asos quota fissa usata nei dettagli. Andrebbe trasformato in vero foglio parametri con riferimenti, oppure eliminato.

### 7.2 Formula TOTALE FATTURA (F139) sporca
`=F122+F82+F74+F79+F69++F80127+F131+F126+F134+F137+F121+F135+K130`
- `F122`, `K130` = celle vuote (0)
- `F80127` = riferimento alla riga 80.127 della colonna F (probabile errore di digitazione di "F80+F127" o simile), vuota → 0
- doppio operatore `++`
Il risultato numerico oggi è corretto (coincide con F138+F137+F135), ma la formula è fragilissima: basta scrivere qualcosa in una di quelle celle per falsare il totale. **Da riscrivere come `=F138+F137+F135`.**

### 7.3 DICEMBRE-GENNAIO: primo mese azzerato → risparmio gonfiato
Nel foglio DICEMBRE-GENNAIO tutte le quantità del "DETTAGLIO DICEMBRE" (D52:D68, D71:D73, ecc.) sono **0**: la fattura simulata contiene di fatto **un solo mese** (Gennaio, 44,28 €). Ma viene confrontata con `E33 = 137,06 €`, che è un importo **bimestrale** del vecchio fornitore. Il risparmio di 92,78 €/bimestre (556,70 €/anno) è quindi **non omogeneo e sovrastimato**: si confronta 1 mese contro 2 mesi.

### 7.4 Etichette mesi incoerenti (copia-incolla)
- Foglio GENNAIO-FEBBRAIO: i due dettagli sono intitolati "DETTAGLIO **FEBBRAIO**" e "DETTAGLIO **MARZO**".
- `E15` ("Data Riferimento") riporta "DIC.2024-GEN.2025" **in tutti e tre i fogli**, mai aggiornata.
- Nel foglio NOVEMBRE-DICEMBRE il secondo dettaglio è "DETTAGLIO DICEMBRE" ma il foglio DICEMBRE-GENNAIO ha un altro "DETTAGLIO DICEMBRE" (azzerato): possibile doppio conteggio concettuale dello stesso mese tra fogli.

### 7.5 DISPBT hardcoded a 0
In DICEMBRE-GENNAIO e GENNAIO-FEBBRAIO la cella `F53` (DISPBT mese 1) è un **valore fisso 0** invece della formula `=C53*D53`. In GENNAIO-FEBBRAIO, con C53=0,109858 e D53=1, mancano ~0,11 € dal totale. Nel mese 2 la formula c'è ma è `=C105` (ignora D105) invece di `=C105*D105`.

### 7.6 Novembre con struttura tariffaria diversa
Nel foglio NOVEMBRE-DICEMBRE il dettaglio di Novembre usa nomi voce e valori diversi (Commercializzazione 20 €/mese, nota "SPREAD 0,01", prezzi F1/F2/F3 più bassi) rispetto a Dicembre (Commercializzazione 8,95 €/mese). Sembra che Novembre rappresenti la tariffa di un fornitore/offerta diversa da Dicembre: da chiarire se voluto (transizione tra fornitori a metà bimestre) o refuso.

### 7.7 Percentuali del quadro sintetico anomale con partite negative
In GENNAIO-FEBBRAIO le percentuali E23:E26 sono calcolate su C29 che include l'accredito di −11,40 €: il trasporto risulta al 55% e le altre partite al −19%, e la somma delle voci supera il 100%. Matematicamente coerente ma comunicativamente fuorviante.

### 7.8 IVA effettiva ≠ 1/11 del totale quando ci sono partite esenti
In GENNAIO-FEBBRAIO `E28 = C28/C29 = 10,84%` invece del 9,09% degli altri fogli, per effetto della partita esente da −11,40 €. Non è un errore, ma va spiegato a chi legge.

### 7.9 Perdite di rete arrotondate all'intero
`ROUND(kWh×10%; 0)` introduce arrotondamenti grossolani su consumi piccoli (es. 2 kWh → perdite 0). Il metodo standard ARERA usa il 10,2% senza arrotondamento all'intero. Impatto piccolo ma sistematico.

### 7.10 Nessuna cella di input marcata
Non c'è distinzione visiva/strutturale tra celle di input (prezzi C, quantità D, importo vecchio fornitore E33, partite F135) e celle calcolate. Chi usa il simulatore rischia di sovrascrivere formule (è già successo: F53).

## 8. Flusso logico complessivo (riassunto per Manus)

```
INPUT UTENTE (per ciascuno dei 2 mesi del bimestre):
  • Prezzi unitari nuovo fornitore (colonna C)
  • kWh consumati per fascia F1/F2/F3 (D54:D56, D106:D108)
  • Potenza impegnata kW (D71/D123)
  • Eventuali partite non soggette a IVA (F135)
  • Importo bolletta bimestrale VECCHIO fornitore (E33)

CALCOLI AUTOMATICI:
  1. Perdite di rete = 10% kWh per fascia (arrotondato), stesso prezzo della fascia
  2. kWh "fiscali" = kWh fasce + perdite → base per dispacciamento
  3. Ogni voce: Totale = Corrispettivo × Quantità
  4. 4 subtotali per mese: Materia energia / Trasporto / Oneri sistema / Imposte
  5. Imponibile = somma degli 8 subtotali dei 2 mesi
  6. IVA = 10% dell'imponibile
  7. Totale fattura = Imponibile + IVA + Partite non soggette a IVA
  8. Risparmio bimestrale = Bolletta vecchio fornitore − Totale fattura
  9. Risparmio annuale = Risparmio bimestrale × 6
```

## 9. Raccomandazioni prioritarie di intervento

1. **Riscrivere F139** come `=F138+F137+F135` in tutti i fogli (elimina i riferimenti spuri).
2. **Correggere F53/F105** con `=C53*D53` e `=C105*D105`.
3. **Allineare i periodi**: o si compilano entrambi i mesi di DICEMBRE-GENNAIO, o si confronta con un importo mensile del vecchio fornitore (altrimenti il risparmio è falsato).
4. **Correggere le etichette** dei mesi e la Data Riferimento in ogni foglio.
5. **Centralizzare i parametri** nel foglio DATI (Asos, Arim, accisa, dispacciamento) con riferimenti `=DATI!$B$n`, così un aggiornamento tariffario si fa in un punto solo.
6. **Marcare le celle di input** (es. sfondo giallo / testo blu) e proteggere le celle formula.
7. Valutare perdite di rete al 10,2% senza arrotondamento all'intero.
8. Aggiungere un foglio riepilogo che sommi i 3 bimestri per un risparmio annuale "reale" invece dell'estrapolazione ×6 da un singolo bimestre.

---

*Analisi generata il 29/07/2026 su SIMULATORE_BIMESTRALE_LUCE.xlsx (4 fogli, ~130 righe attive per foglio bimestrale). Tutti i valori e le formule citati sono stati letti direttamente dal file.*
