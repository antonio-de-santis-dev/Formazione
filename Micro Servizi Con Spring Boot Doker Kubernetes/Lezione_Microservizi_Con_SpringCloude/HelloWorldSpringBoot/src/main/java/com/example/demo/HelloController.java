package com.example.demo;


import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/api")
public class HelloController {
	
	@Value("${messaggio}")
	private String tmp;

	
	
	
	// ------------------- Ricerca Per Codice ------------------------------------
	//api/ciao
		@RequestMapping(value = "/ciao", method = RequestMethod.GET, produces = "application/json")
		public ResponseEntity<String> helloWorld()  
				
		{
			System.out.println("tmp:"+tmp);
			System.out.println("****** dentro *******");


			return new ResponseEntity<String>("\"ciao mamma\"", HttpStatus.OK);
		}

		@RequestMapping(value = "/getArticolo", method = RequestMethod.GET, produces = "application/json")
		public ResponseEntity<Articoli> getArticolo()  
				
		{
			System.out.println("****** dentro *******");

			Articoli a = new Articoli();
			a.setDescrizione("coca cola");
			a.setId(2);
			
			return new ResponseEntity<Articoli>(a, HttpStatus.OK);
		}	
		
		
		@GetMapping(value="/elencoArticoli")
		public ResponseEntity<List<Articoli>> elencoArticoli(){
			
			
			List<Articoli> res = new ArrayList<Articoli>();
			
			Articoli a = new Articoli();
			a.setDescrizione("coca cola");
			a.setId(2);
			res.add(a);
			
			
			Articoli a1 = new Articoli();
			a1.setDescrizione("coca cola");
			a1.setId(2);
			res.add(a1);		
	
			
			Articoli a2 = new Articoli();
			a2.setDescrizione("coca cola");
			a2.setId(2);
			res.add(a2);
			
			Articoli a3 = new Articoli();
			a3.setDescrizione("coca cola");
			a3.setId(2);
			res.add(a3);
			
			Articoli a4 = new Articoli();
			a4.setDescrizione("coca cola");
			a4.setId(2);
			res.add(a4);
			
			Articoli a5 = new Articoli();
			a5.setDescrizione("coca cola");
			a5.setId(2);
			res.add(a5);
			
			Articoli a6 = new Articoli();
			a6.setDescrizione("coca cola");
			a6.setId(2);
			res.add(a6);
			
			Articoli a7 = new Articoli();
			a7.setDescrizione("coca cola");
			a7.setId(2);
			res.add(a7);
			
			Articoli a8 = new Articoli();
			a8.setDescrizione("coca cola");
			a8.setId(2);
			res.add(a8);
			
			Articoli a9 = new Articoli();
			a9.setDescrizione("coca cola");
			a9.setId(2);
			res.add(a9);
			
			Articoli a10 = new Articoli();
			a10.setDescrizione("coca cola");
			a10.setId(2);
			res.add(a10);
			
			return new ResponseEntity<List<Articoli>>( res,HttpStatus.OK);
			
				
		}
		
		
		@RequestMapping(value = "/elimina/{codart}", method = RequestMethod.DELETE, produces = "application/json" )
		public ResponseEntity<?> deleteArt(@PathVariable("codart") String CodArt)
		{
			// simulazione cancellazione
			System.out.println("eliminazione articolo con id:"+CodArt);
			ObjectMapper mapper = new ObjectMapper();
			ObjectNode responseNode = mapper.createObjectNode();
			
			responseNode.put("code", HttpStatus.OK.toString());
			responseNode.put("message", "Eliminazione Articolo " + CodArt + " Eseguita Con Successo");
			
			return new ResponseEntity<>(responseNode, new HttpHeaders(), HttpStatus.OK);
					
		}
		
		
		// ------------------- MODIFICA ARTICOLO ------------------------------------
		@RequestMapping(value = "/modifica", method = RequestMethod.PUT)
		public ResponseEntity<?> updateArt( @RequestBody Articoli articolo, BindingResult bindingResult,
					UriComponentsBuilder ucBuilder)  
		{
			
			
//			if (bindingResult.hasErrors())
//			{
//				String MsgErr = errMessage.getMessage(bindingResult.getFieldError(), LocaleContextHolder.getLocale());
//				
//				logger.warn(MsgErr);
//
//				throw new BindingException(MsgErr);
//			}
//			
//			Articoli checkArt =  articoliService.SelArtByCodArt(articolo.getCodArt());
//
//			if (checkArt == null)
//			{
//				String MsgErr = String.format("Articolo %s non presente in anagrafica! "
//						+ "Impossibile utilizzare il metodo PUT", articolo.getCodArt());
//				
//				logger.warn(MsgErr);
//				
//				throw new NotFoundException(MsgErr);
//			}
			
			HttpHeaders headers = new HttpHeaders();
			ObjectMapper mapper = new ObjectMapper();
			
			headers.setContentType(MediaType.APPLICATION_JSON);

			ObjectNode responseNode = mapper.createObjectNode();

		
			
			responseNode.put("code", HttpStatus.OK.toString());
			responseNode.put("message", "Modifica Articolo Eseguita Con Successo");

			return new ResponseEntity<>(responseNode, headers, HttpStatus.CREATED);
		}


}
