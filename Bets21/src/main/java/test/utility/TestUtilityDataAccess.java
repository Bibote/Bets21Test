package test.utility;


import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;

import configuration.ConfigXML;
import domain.Equipo;
import domain.EquipoId;
import domain.Event;
import domain.Pronosticos;
import domain.Question;
import exceptions.PrognosticAlreadyExist;
import exceptions.WrongParameters;

public class TestUtilityDataAccess {
	protected  EntityManager  db;
	protected  EntityManagerFactory emf;

	ConfigXML  c=ConfigXML.getInstance();

	Equipo t1= new Equipo("equipo1", 2022);
	Equipo t2= new Equipo("equipo2", 2022);
	
	
	public TestUtilityDataAccess()  {		
		System.out.println("Creating TestDataAccess instance");

		open();		
	}

	
	public void open(){
		
		System.out.println("Opening TestDataAccess instance ");

		String fileName=c.getDbFilename();
		
		if (c.isDatabaseLocal()) {
			  emf = Persistence.createEntityManagerFactory("objectdb:"+fileName);
			  db = emf.createEntityManager();
		} else {
			Map<String, String> properties = new HashMap<String, String>();
			  properties.put("javax.persistence.jdbc.user", c.getUser());
			  properties.put("javax.persistence.jdbc.password", c.getPassword());

			  emf = Persistence.createEntityManagerFactory("objectdb://"+c.getDatabaseNode()+":"+c.getDatabasePort()+"/"+fileName, properties);

			  db = emf.createEntityManager();
    	   }
		
	}
	public void close(){
		db.close();
		System.out.println("DataBase closed");
	}

	public boolean removeEvent(Event ev) {
		System.out.println(">> DataAccessTest: removeEvent");
		Event e = db.find(Event.class, ev.getEventNumber());
		if (e!=null) {
			db.getTransaction().begin();
			db.remove(e);
			db.getTransaction().commit();
			return true;
		} else 
		return false;
    }
		
		public Event addEventWithQuestion(String desc, Date d, String question, float qty) {
			System.out.println(">> DataAccessTest: addEvent");
			Event ev=null;
				db.getTransaction().begin();
				try {
				    ev=new Event(desc,d, t1,t2);
				    ev.addQuestion(question,  qty, false);
					db.persist(ev);
					db.getTransaction().commit();
				}
				catch (Exception e){
					e.printStackTrace();
				}
				return ev;
	    }
		
		public Vector<Event> getEvents(Date date) {
			System.out.println(">> DataAccess: getEvents");
			Vector<Event> res = new Vector<Event>();	
			TypedQuery<Event> query = db.createQuery("SELECT ev FROM Event ev WHERE ev.eventDate=?1",Event.class);   
			query.setParameter(1, date);
			List<Event> events = query.getResultList();
		 	 for (Event ev:events){
		 	   System.out.println(ev.toString());		 
			   res.add(ev);
			  }
		 	return res;
		}
		
		
		public boolean existQuestion(Event event, String question) {
			System.out.println(">> DataAccess: existQuestion=> event= "+event+" question= "+question);
			Event ev = db.find(Event.class, event.getEventNumber());
			return ev.DoesQuestionExists(question);
			
		}
		
		public Pronosticos createPrognosticTeam(int ev, Question q, Equipo eq, float porcen) throws PrognosticAlreadyExist, WrongParameters {
			Event evento = db.find(Event.class, ev);
			if(evento==null || q==null || eq==null || porcen<0) throw new WrongParameters() ;
			Vector<Question> questions = evento.getQuestions();
			for (Question question : questions) {
				if (question.getQuestionNumber() == q.getQuestionNumber()) {
					if (question.doesPrognosticExists(null,eq))
						throw new PrognosticAlreadyExist();
					db.getTransaction().begin();
					Pronosticos p = question.addPronostico(null, porcen, eq);
					db.persist(evento);
					db.getTransaction().commit();
					return p;

				}
			}
			return null;

		}
		public Event addEvent(Event ev) {
			db.getTransaction().begin();
			db.persist(ev);
			db.getTransaction().commit();
			db.find(Event.class, ev);
			return db.find(Event.class, ev);
		}
}

