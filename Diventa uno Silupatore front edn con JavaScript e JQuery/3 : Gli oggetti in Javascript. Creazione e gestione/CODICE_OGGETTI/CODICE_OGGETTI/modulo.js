var corsa = (function(){
    
        // var km = '10 km per ora';

        var DEFAULTS = {
massima: '20 km per ora',
massimoTempo: '2 ore'
        };
    
        return{

    km: function(){

        var opzioni = arguments[0] || '';
        var parametri = opzioni.massima || DEFAULTS.massima;
    return(parametri);

    
    
    },

    tempiCorsa: function(){
        
                var opzioni1 = arguments[0] || '';
                var parametri1 = opzioni1.massimoTempo || DEFAULTS.massimoTempo;
            return(parametri1);

            
            
            }
    
        };
    
    })();