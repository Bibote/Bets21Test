package test;

import static org.junit.jupiter.api.Assertions.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import configuration.ConfigXML;
import dataAccess.DataAccess;
import domain.Equipo;
import domain.Event;
import domain.Pronosticos;
import domain.Question;
import exceptions.PrognosticAlreadyExist;
import exceptions.WrongParameters;
import test.utility.TestUtilityDataAccess;

class CreatePrognosticTeamDATest {
	static DataAccess sut = new DataAccess(ConfigXML.getInstance().getDataBaseOpenMode().equals("initialize"));;
	static TestUtilityDataAccess testDA = new TestUtilityDataAccess();
	
	

	Equipo eq1= new Equipo("equipo1", 2021);
	Equipo eq2= new Equipo("equipo2", 2021);
	private Question q;
	private Event ev;

	

	@Test
	@DisplayName("Test 1:No existe ninguna pregunta en el evento")
	void testCreatePrognostic1()     {
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		Date oneDate;
		try {
			oneDate = sdf.parse("05/10/2022");
			Event ev= new Event("eventoPrueba",oneDate,eq1,eq2);
		
			testDA.addEvent(ev);
			Question q= new Question("query", 15, ev, true);
			System.out.println(testDA.getEvents(oneDate));
		} catch (ParseException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		try {
			Question que= new Question("pregunta", 15, ev, true);
			assertNull(testDA.createPrognosticTeam(1, que, eq1, 50));

			
			
		} catch (PrognosticAlreadyExist e) {
			fail();
		} catch (WrongParameters e) {
			fail();
		}
		finally {
			Event ev= new Event("eventoPrueba",null,eq1,eq2);
			ev.setEventNumber(1);
			testDA.removeEvent(ev);
		}
		
	
		
		
	}
	@Test
	@DisplayName("Test 2:solo hay 1 pregunta y no es la que buscamos")
	void testCreatePrognostic2()     {
		
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		Date oneDate;
		try {
			oneDate = sdf.parse("05/10/2022");
			Event ev= new Event("eventoPrueba",oneDate,eq1,eq2);
			
			Question q= new Question("query", 15, ev, true);
			ev.addQuestion(q);
			testDA.addEvent(ev);
			try {
				Question que= new Question("pregunta", 15, ev, true);
				assertNull(testDA.createPrognosticTeam(2, que, eq1, 50));
			} catch (PrognosticAlreadyExist e) {
				fail();
			} catch (WrongParameters e) {
				fail();
			}
			
		

		} catch (ParseException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		finally {
			Event ev= new Event("eventoPrueba",null,eq1,eq2);
			ev.setEventNumber(2);
			testDA.removeEvent(ev);
		}

		
	}
	
	@Test
	@DisplayName("Test 3:Ya existe nuestro pronostico")
	void testCreatePrognostic3()     {
		
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		Date oneDate;
		try {
			oneDate = sdf.parse("05/10/2022");
			Event ev= new Event("eventoPrueba",oneDate,eq1,eq2);
			
			Question q= new Question("query", 15, ev, true);
			q.addPronostico("pronos", 20, eq1);
			ev.addQuestion(q);
			testDA.addEvent(ev);
			assertThrows(PrognosticAlreadyExist.class, 
					()->testDA.createPrognosticTeam(4, q, eq1, 15));
		

		} catch (ParseException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		finally {
			Event ev= new Event("eventoPrueba",null,eq1,eq2);
			ev.setEventNumber(4);
			testDA.removeEvent(ev);
		}

	}
	
	@Test
	@DisplayName("Test 4:No existe el pronostico que queremos meter")
	void testCreatePrognostic4()     {
		
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		Date oneDate;
		try {
			oneDate = sdf.parse("05/10/2022");
			Event ev= new Event("eventoPrueba",oneDate,eq1,eq2);
			
			Question q= new Question("query", 15, ev, true);
			ev.addQuestion(q);
			testDA.addEvent(ev);
			float porcen= 50;
			try {
				Pronosticos pro = testDA.createPrognosticTeam(7, q, eq1, porcen);
				assertEquals(eq1,pro.getEq());
				assertEquals(porcen,pro.getPorcentaje());
				assertEquals(q,pro.getQuestion());
			} catch (PrognosticAlreadyExist e) {
				fail();
			} catch (WrongParameters e) {
				fail();
			}
			
		

		} catch (ParseException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		finally {
			Event ev= new Event("eventoPrueba",null,eq1,eq2);
			ev.setEventNumber(7);
			testDA.removeEvent(ev);
		}

		
	}
	
	@Test
	@DisplayName("Test 5:No existe ninguna pregunta en el evento")
	void testCreatePrognostic5()     {
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		Date oneDate;
		try {
			oneDate = sdf.parse("05/10/2022");
			Event ev= new Event("eventoPrueba",oneDate,eq1,eq2);
		
			testDA.addEvent(ev);
			Question q= new Question("query", 15, ev, true);
			System.out.println(testDA.getEvents(oneDate));
			assertThrows(WrongParameters.class, 
					()->testDA.createPrognosticTeam(45, q, eq1, 15));
			assertThrows(WrongParameters.class, 
					()->testDA.createPrognosticTeam(10, null, eq1, 15));
			assertThrows(WrongParameters.class, 
					()->testDA.createPrognosticTeam(10, q, null, 15));
			assertThrows(WrongParameters.class, 
					()->testDA.createPrognosticTeam(10, q, eq1, -1));
		} catch (ParseException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		
		finally {
			Event ev= new Event("eventoPrueba",null,eq1,eq2);
			ev.setEventNumber(10);
			testDA.removeEvent(ev);
		}
	}
		


}
