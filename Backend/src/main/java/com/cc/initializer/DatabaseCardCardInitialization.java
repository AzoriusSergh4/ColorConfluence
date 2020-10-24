package com.cc.initializer;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import io.magicthegathering.javasdk.api.CardAPI;
import io.magicthegathering.javasdk.api.SetAPI;
import io.magicthegathering.javasdk.exception.HttpRequestFailedException;
import io.magicthegathering.javasdk.resource.Card;
import io.magicthegathering.javasdk.resource.MtgSet;

@Component
public class DatabaseCardCardInitialization {
	
	public static final Logger logger = LogManager.getLogger();
	private int intento = 1;
	
	@Autowired
	CardDAO cardDao;
	
	@PostConstruct
	public void storeCards() {
		
		logger.info("RESETEO DE BBDD");
		//cardDao.resetCards();
		//En caso de meter todas las colecciones
	/*List<MtgSet> sets = SetAPI.getAllSets();
		for(MtgSet set : sets) {
			logger.info("INSERCIÓN DE " + set.getCode());
			storeSet(set.getCode());
		}*/

		logger.info("INICIALIZACIÓN COMPLETADA CON ÉXITO");
	}
	
	private void storeSet(String set) {
		
		try {
			logger.info("Intento " + intento + " en la llamada a la API (" + set + ")");
			MtgSet currentSet = SetAPI.getSet(set); 
			logger.info("LLamada realizada con éxito.");
			logger.info("Insertando cartas de " + set +" en BBDD");
			cardDao.storeCards(currentSet.getCards());
			logger.info("Inserción realizada con éxito");
		}catch (HttpRequestFailedException ex) {
			logger.info("No se ha realizado la llamada");
			if(intento < 6) {
				intento++;
				logger.info("Volviendo a intentar");
				storeSet(set);
			}else {
				logger.info("Superado el límite de intentos. La llamada no se ha realizado");
			}
		}
		finally {
			intento = 1;
		}
		
	}
	
	
}
