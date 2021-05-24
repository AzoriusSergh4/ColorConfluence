package com.cc.initializer;

import com.cc.web.entity.SetInfo;
import com.cc.web.set.SetInfoService;
import io.magicthegathering.javasdk.api.SetAPI;
import io.magicthegathering.javasdk.exception.HttpRequestFailedException;
import io.magicthegathering.javasdk.resource.MtgSet;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.List;

@Component
public class DatabaseCardCardInitialization {
	
	public static final Logger logger = LogManager.getLogger();
	private int intento = 1;
	
	@Autowired
	CardDAO cardDao;

	@Autowired
	private SetInfoService setInfoService;
	
	@PostConstruct
	public void storeCards() {

		List<MtgSet> sets = SetAPI.getAllSets();
		for(MtgSet set : sets) {
			logger.info("COMPROBACIÓN DE LA COLECCIÓN {} EN BBDD", set.getCode());
			if(!setInfoService.existsByCode(set.getCode())){
				logger.info("LA COLECCIÓN {} NO EXISTE EN BBDD", set.getCode());
				logger.info("INSERCIÓN DE {}", set.getCode());
				storeSet(set.getCode());
				setInfoService.save(new SetInfo(set.getName(), set.getCode()));
			}else {
				logger.info("LA COLECCIÓN {} YA EXISTE EN BBDD", set.getCode());
			}
		}

		logger.info("INICIALIZACIÓN COMPLETADA CON ÉXITO");
	}
	
	private void storeSet(String set) {
		
		try {
			logger.info("Intento {} en la llamada a la API ({})", intento, set);
			MtgSet currentSet = SetAPI.getSet(set); 
			logger.info("LLamada realizada con éxito.");
			logger.info("Insertando cartas de {} en BBDD", set);
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
