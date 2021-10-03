package test;


import static org.junit.Assert.assertNull;
import static org.junit.jupiter.api.Assertions.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import business.logic.BLFacade;
import business.logic.BLFacadeImplementation;
import dataAccess.DataAccess;
import domain.Equipo;
import domain.Event;
import domain.Pronosticos;
import domain.Question;
import exceptions.PrognosticAlreadyExist;
import exceptions.WrongParameters;

class CreatePrognosticTeamBLMockTest {
	
	DataAccess dataAccess = Mockito.mock(DataAccess.class);
	Event mockedEvent = Mockito.mock(Event.class);

	BLFacade sut = new BLFacadeImplementation(dataAccess);

	@Test
	@DisplayName("Test1: No existe ese evento en la bd")
	void test1()     {
	

			try {
				// configure Mock
Mockito.when(dataAccess.createPrognosticTeam(Mockito.anyInt(), Mockito.any(Question.class), Mockito.any(Equipo.class), Mockito.anyFloat())).
thenThrow(new WrongParameters());

				// invoke System Under Test (sut)
				String queryText = "Query Text";

				Equipo e= new Equipo("real", 0);
				assertThrows(WrongParameters.class, ()-> sut.createPrognostic(1, new Question(), queryText, 15, e));


			} catch (PrognosticAlreadyExist e) {
			
			} catch (WrongParameters e1) {
				
			} 
	

	}
	@Test
	@DisplayName("Test2: La pregunta es null")
	void test2()     {
		
			try {
				// configure Mock
Mockito.when(dataAccess.createPrognosticTeam(Mockito.anyInt(), Mockito.any(), Mockito.any(Equipo.class), Mockito.anyFloat())).
thenThrow(new WrongParameters());

				// invoke System Under Test (sut)
				String queryText = "Query Text";
				Equipo e= new Equipo("real", 0);
				assertThrows(WrongParameters.class, ()-> sut.createPrognostic(1, null, queryText, 15, e));


			} catch (PrognosticAlreadyExist e) {
			
			} catch (WrongParameters e1) {
				
			} 
		

	}
	@Test
	@DisplayName("Test3: La pregunta  no se encuentra en el evento")
	void test3()     {
		
			try {
				// configure Mock
Mockito.when(dataAccess.createPrognosticTeam(Mockito.anyInt(), Mockito.any(Question.class), Mockito.any(Equipo.class), Mockito.anyFloat())).
thenReturn(null);

				// invoke System Under Test (sut)
				String queryText = "Query Text";
				Equipo e= new Equipo("real", 0);
				assertNull(sut.createPrognostic(1, new Question(), queryText, 15, e));


			} catch (PrognosticAlreadyExist e) {
			
			} catch (WrongParameters e1) {
				
			} 
		

	}
	@Test
	@DisplayName("Test4:Float<0")
	void test4()     {
	

			try {
				// configure Mock
Mockito.when(dataAccess.createPrognosticTeam(Mockito.anyInt(), Mockito.any(Question.class), Mockito.any(), Mockito.anyFloat())).
thenThrow(new WrongParameters());

				// invoke System Under Test (sut)
				String queryText = "Query Text";

			
				Equipo e= new Equipo("real", 0);
				assertThrows(WrongParameters.class, ()-> sut.createPrognostic(1, new Question(), queryText, -15, e));


			} catch (PrognosticAlreadyExist e) {
			
			} catch (WrongParameters e1) {
				
			} 
	

	}
	
	@Test
	@DisplayName("Test5:Pronostico repetido")
	void test5()     {
	

			try {
				// configure Mock
Mockito.when(dataAccess.createPrognosticTeam(Mockito.anyInt(), Mockito.any(Question.class), Mockito.any(), Mockito.anyFloat())).
thenThrow(new PrognosticAlreadyExist());

				// invoke System Under Test (sut)
				String queryText = "Query Text";

			
				Equipo e= new Equipo("real", 0);
				assertThrows(PrognosticAlreadyExist.class, ()-> sut.createPrognostic(1, new Question(), queryText, 16, e));


			} catch (PrognosticAlreadyExist e) {
			
			} catch (WrongParameters e1) {
				
			} 
	}
	

	
	
	@Test
	@DisplayName("Test6: Todo correcto")
	void test6()     {
		
			String queryText = "Query Text";

			Equipo eq= new Equipo("real", 0);
			Question q= new Question();


			try {
				// configure Mock
				
				Mockito.when(dataAccess.createPrognosticTeam(Mockito.anyInt(), Mockito.any(), Mockito.any(Equipo.class), Mockito.anyFloat())).
				thenReturn(new Pronosticos(eq,15,q));
		
				// invoke System Under Test (sut)
				
				Pronosticos p=sut.createPrognostic(1, q, queryText, 15, eq);
				assertEquals(eq, p.getEq());
				assertEquals(15, p.getPorcentaje());
				assertEquals(q, p.getQuestion());


			} catch (PrognosticAlreadyExist e) {
			
			} catch (WrongParameters e1) {
				
			} 
	
	
		}
	
	@Test
	@DisplayName("Test7 se crea un pronostico sin equipos")
	void test7()     {
		
			String queryText = "Query Text";

			Equipo eq= new Equipo("real", 0);
			Question q= new Question();


			try {
				// configure Mock
				
				Mockito.when(dataAccess.createPrognostic(Mockito.anyInt(), Mockito.any(), Mockito.anyString(), Mockito.anyFloat())).
				thenReturn(new Pronosticos(queryText, 15, q));
		
				// invoke System Under Test (sut)
				
				Pronosticos p=sut.createPrognostic(1, q, queryText, 15, null);
				assertEquals(queryText, p.getPronostico());
				assertEquals(15, p.getPorcentaje());
				assertEquals(q, p.getQuestion());


			} catch (PrognosticAlreadyExist e) {
			
			} catch (WrongParameters e1) {
				
			} 
	
	
		}
		

}
