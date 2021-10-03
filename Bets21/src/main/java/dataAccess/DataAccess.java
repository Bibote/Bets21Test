package dataAccess;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.Vector;


import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.RollbackException;
import javax.persistence.TypedQuery;

import configuration.ConfigXML;
import configuration.UtilDate;
import domain.Apuesta;
import domain.Boleto;
import domain.Card;
import domain.Equipo;
import domain.EquipoId;
import domain.Event;
import domain.Payment;
import domain.Pronosticos;
import domain.Question;
import domain.User;
import exceptions.BoletoNoExiste;
import exceptions.BoletoUsado;
import exceptions.CodigoRepetido;
import exceptions.MaxUsed;
import exceptions.NotEnoughChuti;
import exceptions.PrognosticAlreadyExist;
import exceptions.QuestionAlreadyExist;
import exceptions.UserAlreadyExist;
import exceptions.UserDoesntExist;
import exceptions.WrongParameters;

/**
 * It implements the data access to the objectDb database
 */
public class DataAccess {
	protected static EntityManager db;
	protected static EntityManagerFactory emf;

	private int adminDNI = 11223344;

	ConfigXML c = ConfigXML.getInstance();

	public DataAccess(boolean initializeMode) {

		System.out.println("Creating DataAccess instance => isDatabaseLocal: " + c.isDatabaseLocal()
		+ " getDatabBaseOpenMode: " + c.getDataBaseOpenMode());

		open(initializeMode);

	}

	public DataAccess() {
		this(false);
	}

	/**
	 * This is the data access method that initializes the database with some events
	 * and questions. This method is invoked by the business logic (constructor of
	 * BLFacadeImplementation) when the option "initialize" is declared in the tag
	 * dataBaseOpenMode of resources/config.xml file
	 */
	public void initializeDB() {

		db.getTransaction().begin();
		try {

			Calendar today = Calendar.getInstance();

			int month = today.get(Calendar.MONTH);
			month += 1;
			int year = today.get(Calendar.YEAR);
			if (month == 12) {
				month = 0;
				year += 1;
			}

			this.guardarEquiposDeCsv(null, true);//db.getTransaction().begin();



			Event ev1 = new Event("Partido liga", UtilDate.newDate(year, month, 17), this.obtenerEquipo("Atletico", 2021), this.obtenerEquipo("Athletic", 2021));
			Event ev2 = new Event("Partido liga", UtilDate.newDate(year, month, 17), this.obtenerEquipo("Eibar", 2021), this.obtenerEquipo("Barcelona", 2021));
			Event ev3 = new Event("Partido liga", UtilDate.newDate(year, month, 17), this.obtenerEquipo("Getafe", 2021), this.obtenerEquipo("Celta", 2021));
			Event ev4 = new Event("Partido liga", UtilDate.newDate(year, month, 17), this.obtenerEquipo("Alaves", 2021), this.obtenerEquipo("Villareal", 2021));

			Event ev5 = new Event("Partido liga", UtilDate.newDate(year, month+1, 26), this.obtenerEquipo("Atletico", 2021), this.obtenerEquipo("Athletic", 2021));
			Event ev6 = new Event("Partido liga", UtilDate.newDate(year, month+1, 26), this.obtenerEquipo("Eibar", 2021), this.obtenerEquipo("Barcelona", 2021));
			Event ev7 = new Event("Partido liga", UtilDate.newDate(year, month+1, 26), this.obtenerEquipo("Getafe", 2021), this.obtenerEquipo("Celta", 2021));
			Event ev8 = new Event("Partido liga", UtilDate.newDate(year, month+1, 26), this.obtenerEquipo("Alaves", 2021), this.obtenerEquipo("Villareal", 2021));




			Question q1;
			Question q2;
			Question q3;
			Question q4;
			Question q5;
			Question q6;

			if (Locale.getDefault().equals(new Locale("es"))) {
				q1 = ev1.addQuestion("¿Quien ganara el partido?", 1, false);
				q2 = ev1.addQuestion("¿Quien metera el primer gol?", 2, false);
				q3 = ev1.addQuestion("¿Quien ganara el partido?", 1, false);
				q4 = ev5.addQuestion("¿Cuantos goles se marcarï¿½n?", 2, false);
				q5 = ev5.addQuestion("¿Quien ganara el partido?", 1, false);
				q6 = ev5.addQuestion("¿Habra goles en la primera parte?", 2, false);
			} else if (Locale.getDefault().equals(new Locale("en"))) {
				q1 = ev1.addQuestion("Who will win the match?", 1, false);
				q2 = ev1.addQuestion("Who will score first?", 2, false);
				q3 = ev1.addQuestion("Who will win the match?", 1, false);
				q4 = ev5.addQuestion("How many goals will be scored in the match?", 2, false);
				q5 = ev5.addQuestion("Who will win the match?", 1, false);
				q6 = ev5.addQuestion("Will there be goals in the first half?", 2, false);
			} else {
				q1 = ev1.addQuestion("Zeinek irabaziko du partidua?", 1, false);
				q2 = ev1.addQuestion("Zeinek sartuko du lehenengo gola?", 2, false);
				q3 = ev1.addQuestion("Zeinek irabaziko du partidua?", 1, false);
				q4 = ev5.addQuestion("Zenbat gol sartuko dira?", 2, false);
				q5 = ev5.addQuestion("Zeinek irabaziko du partidua?", 1, false);
				q6 = ev5.addQuestion("Golak sartuko dira lehenengo zatian?", 2, false);

			}


			db.persist(ev1);
			db.persist(ev2);
			db.persist(ev3);
			db.persist(ev4);
			db.persist(ev5);
			db.persist(ev6);
			db.persist(ev7);
			db.persist(ev8);

			User admin = new User(adminDNI, "1234567", "Admin", "Administrador", "SuperUser", "admin@ehu.eus",
					UtilDate.newDate(1998, 4, 9));//b
			User elpepe = new User(123456789, 12345678, "1234567", "Pepe", "Lope", "deVega", "elpepe@ehu.eus",
					UtilDate.newDate(1998, 4, 9));//z
			User emue = new User(11111111, 11111111, "1234567", "emue", "Lope", "deVega", "elpepe@ehu.eus",
					UtilDate.newDate(1998, 4, 9));//h

			admin.setChutiGoles(1000.0);
			elpepe.setChutiGoles(10.0);
			emue.setChutiGoles(100.0);

			db.persist(admin);
			db.persist(elpepe);
			db.persist(emue);

			db.getTransaction().commit();
			System.out.println("Db initialized");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public List<Equipo> obtenerEquipos(int temporada){

		TypedQuery<Equipo> query = db.createQuery("SELECT e FROM Equipo e WHERE e.temporada=?1", Equipo.class);
		query.setParameter(1, temporada);
		List<Equipo> equipos = query.getResultList();
		return equipos;

	}


	public void guardarEquiposDeCsv(String path, boolean estado) throws IOException {

		if (path == null) {
			path = "src/main/resources/equipos.csv";
		}
		BufferedReader csvReader = new BufferedReader(new FileReader(path));
		String row;
		while((row = csvReader.readLine()) != null){
			String[] data = row.split(",");
			Equipo eq1 = new Equipo(data[0], Integer.parseInt(data[1]));
			eq1.setFundacion(Integer.parseInt(data[2]));
			eq1.setSede(data[3]);
			eq1.setAforo(Integer.parseInt(data[4]));
			eq1.setPresidente(data[5]);
			eq1.setEntrenador(data[6]);
			eq1.setWeb(data[7]);
			saveEquipo(eq1, estado);
		}
		csvReader.close();
	}

	public Equipo obtenerEquipo(String nombre, int temporada) {

		EquipoId id = new EquipoId(nombre, temporada);
		Equipo eq1 = db.find(Equipo.class, id);
		return eq1;
	}

	public Equipo saveEquipo(Equipo eq, boolean estado) throws RollbackException{

		if (estado == true) {
			db.persist(eq);
			System.out.println("Se ha guardado el equipo: "+ eq.toString());
			return eq;
		}
		db.getTransaction().begin();
		db.persist(eq);
		db.getTransaction().commit();
		System.out.println("Se ha guardado el equipo: "+ eq.toString());
		return eq;
	}

	public Equipo editarPartidosEquipo(Equipo eq, int tipo) {
		EquipoId id = new EquipoId(eq.getNombre(), eq.getTemporada());
		Equipo eq1 = db.find(Equipo.class, id);
		db.getTransaction().begin();
		switch (tipo) {
		case 0:
			eq1.addEvGanados();
			break;
		case 1:
			eq1.addEvEmpates();
			break;
		case 2:
			eq1.addEvPerdidos();
			break;
		case 3:
			eq1.subEvGanados();
			break;
		case 4:
			eq1.subEvEmpates();
			break;
		case 5:
			eq1.subEvPerdidos();
			break;
		}
		
		db.persist(eq1);
		db.getTransaction().commit();
		System.out.println("Se ha actualizado el equipo: "+eq1.getNombre() );
		return eq1;
		
	}	

	/**
	 * This method creates a question for an event, with a question text and the
	 * minimum bet
	 * 
	 * @param event      to which question is added
	 * @param question   text of the question
	 * @param betMinimum minimum quantity of the bet
	 * @return the created question, or null, or an exception
	 * @throws QuestionAlreadyExist if the same question already exists for the
	 *                              event
	 * @throws WrongParameters if the event, query or boolean is null
	 */
	public Question createQuestion(Event event, String question, float betMinimum, Boolean equipo) throws QuestionAlreadyExist, WrongParameters {
		System.out.println(">> DataAccess: createQuestion=> event= " + event + " question= " + question + " betMinimum="
				+ betMinimum);
		if(event==null || question==null || betMinimum<0 || equipo==null) throw new WrongParameters();
		Event ev = db.find(Event.class, event.getEventNumber());

		if (ev.DoesQuestionExists(question))
			throw new QuestionAlreadyExist(ResourceBundle.getBundle("Etiquetas").getString("ErrorQueryAlreadyExist"));

		db.getTransaction().begin();
		Question q = ev.addQuestion(question, betMinimum, equipo);
		q.setEvent(ev);
		System.out.println(q);
		// db.persist(q);
		db.persist(ev); // db.persist(q) not required when CascadeType.PERSIST is added in questions
		// property of Event class
		// @OneToMany(fetch=FetchType.EAGER, cascade=CascadeType.PERSIST)

		db.getTransaction().commit();
		return q;

	}

	/**
	 * Metodo que crea y guarda en la base de datos un pronostico
	 * 
	 * @param ev     valor del evento
	 * @param q      valor de la pregunta
	 * @param pronos String que representa el pronostico
	 * @param porcen float que representa el porcentaje de la apuesta
	 * @return pronostico
	 * @throws PrognosticAlreadyExist si ya existe ese mismo pronostico en la
	 *                                pregunta
	 */
	public Pronosticos createPrognostic(int ev, Question q, String pronos, float porcen) throws PrognosticAlreadyExist {
		System.out.println(
				">> DataAccess: createPronostic=> Question= " + q + " prognostic= " + pronos + " Porcentaje=" + porcen);
		Event evento = db.find(Event.class, ev);
		Vector<Question> questions = evento.getQuestions();
		for (Question question : questions) {
			if (question.getQuestionNumber() == q.getQuestionNumber()) {

				if (question.doesPrognosticExists(pronos, null))
					throw new PrognosticAlreadyExist();
				db.getTransaction().begin();
				Pronosticos p = question.addPronostico(pronos, porcen, null);
				db.persist(evento);
				db.getTransaction().commit();
				return p;

			}
		}
		return null;

	}
	/**
	 * Funcion para introducir un pronostico con equipo a una pregunta
	 * @param ev Evento al cual se le introduce el pronostico
	 * @param q Pregunta a la cual se le introduce el pronostico
	 * @param eq Equipo al que se le añade el pronostico
	 * @param porcen Porcentaje del pronostico
	 * @return devuelve el pronostico si se ha creado correctamente, o null si no.
	 * @throws PrognosticAlreadyExist Error si ya existe ese pronostico
	 * @throws WrongParameters Error si algunos de los parametros introducidos es incorrecto
	 */
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

	public Vector<Equipo>getEquipoFromQuestion(int q){
		Question q1 = db.find(Question.class, q );
		Vector<Equipo> equipos = new Vector<Equipo>();
		equipos.add(q1.getEvent().getEq1());
		equipos.add(q1.getEvent().getEq2());
		return equipos;

	}




	/**
	 * Metodo que borra un evento en la base de datos
	 * 
	 * @param event Event que representa un evento a eliminar
	 */
	public void deleteEvent(Event event) {
		Event ev = db.find(Event.class, event.getEventNumber());
		db.getTransaction().begin();
		db.remove(ev);
		db.getTransaction().commit();
		System.out.println(">> DataAccess: " + ev.toString() + " removed");
	}

	/**
	 * Metodo que cambia la descripcion de un evento de la base de datos
	 * 
	 * @param event Event que representa el evento al cual cambiar la descripcion
	 * @param s     String que representa la nueva descripcion
	 */
	public void changeEventDescription(Event event, String s) {
		Event ev = db.find(Event.class, event.getEventNumber());
		db.getTransaction().begin();
		ev.setDescription(s);
		db.persist(ev);
		db.getTransaction().commit();
		System.out.println(">> DataAccess: " + event.toString() + " changed by " + ev.toString());

	}

	/**
	 * Metodo que cambia la fecha de un evento de la base de datos
	 * 
	 * @param event Event que representa el evento a cambiar
	 * @param dt    Date que representa la nueva fecha del evento
	 */
	public void changeEventDate(Event event, Date dt) {
		Event ev = db.find(Event.class, event.getEventNumber());
		db.getTransaction().begin();
		ev.setEventDate(dt);
		db.persist(ev);
		db.getTransaction().commit();
		System.out.println(">> DataAccess: " + event.toString() + "date " + event.getEventDate() + " changed by "
				+ ev.toString() + "date " + ev.getEventDate());

	}

	/**
	 * This method retrieves from the database the events of a given date
	 * 
	 * @param date in which events are retrieved
	 * @return collection of events
	 */
	public Vector<Event> getEvents(Date date) {
		System.out.println(">> DataAccess: getEvents");
		Vector<Event> res = new Vector<Event>();
		TypedQuery<Event> query = db.createQuery("SELECT ev FROM Event ev WHERE ev.eventDate=?1", Event.class);
		query.setParameter(1, date);
		List<Event> events = query.getResultList();
		for (Event ev : events) {
			System.out.println(ev.toString());
			res.add(ev);
		}
		return res;
	}

	public Vector<Event> getEventsBetweenDates(Date pastDate, Date todayDate) {
		System.out.println(">> DataAccess: getEventsBetweenDates ");
		Vector<Event> res = new Vector<Event>();
		TypedQuery<Event> query = db.createQuery("SELECT ev FROM Event ev WHERE ev.eventDate>=?1 AND ev.eventDate<?2", Event.class);
		query.setParameter(1, pastDate);
		query.setParameter(2, todayDate);
		List<Event> events = query.getResultList();
		for (Event ev : events) {
			//System.out.println(ev.toString());
			res.add(ev);
		}
		return res;
	}



	/**
	 * This method retrieves from the database the dates a month for which there are
	 * events
	 * 
	 * @param date of the month for which days with events want to be retrieved
	 * @return collection of dates
	 */
	public Vector<Date> getEventsMonth(Date date) {
		System.out.println(">> DataAccess: getEventsMonth");
		Vector<Date> res = new Vector<Date>();

		Date firstDayMonthDate = UtilDate.firstDayMonth(date);
		Date lastDayMonthDate = UtilDate.lastDayMonth(date);

		TypedQuery<Date> query = db.createQuery(
				"SELECT DISTINCT ev.eventDate FROM Event ev WHERE ev.eventDate BETWEEN ?1 and ?2", Date.class);
		query.setParameter(1, firstDayMonthDate);
		query.setParameter(2, lastDayMonthDate);
		List<Date> dates = query.getResultList();
		for (Date d : dates) {
			System.out.println(d.toString());
			res.add(d);
		}
		return res;
	}

	/**
	 * Metodo que crea un usuario y lo aï¿½ade a la base de datos
	 * 
	 * @param usr User que representa el usuario a crear
	 * @return usuario tipo User que rea
	 * @throws UserAlreadyExist Excepcion para saber cuando un usuario ya esta en la
	 *                          base de datos
	 */
	public User createUser(User usr) throws UserAlreadyExist {

		User us = db.find(User.class, usr.getDNI());
		if (us == null) {

			db.getTransaction().begin();
			db.persist(usr);
			db.getTransaction().commit();
			return usr;

		}
		throw new UserAlreadyExist("ErrorUserAlreadyExist");
	}

	/**
	 * Elimina un usuario
	 * @param usr usuario a eliminar
	 * @throws UserDoesntExist si el usuario no existe
	 */
	public void deleteUser(User usr) throws UserDoesntExist {
		User us = db.find(User.class, usr.getDNI());
		if (us != null) {
			db.getTransaction().begin();
			db.remove(us);
			db.getTransaction().commit();
			return;
		}
		throw new UserDoesntExist();
	}

	/**
	 * Banea un usuario pero no lo elimina de la base de datos
	 * @param usr el usuario a banear
	 * @param message el mensaje de baneo
	 * @throws UserDoesntExist lanza esto si no existe
	 */
	public void banUser(User usr, String message) throws UserDoesntExist {
		User us = db.find(User.class, usr.getDNI());
		if (us != null) {
			db.getTransaction().begin();
			us.setMessage(message);
			us.setBan(true);
			db.persist(us);
			db.getTransaction().commit();
			return;
		}
		throw new UserDoesntExist();
	}

	/**
	 * Cambia los creditos positiva o negativamente
	 * @param usr el usuario del cual se cambian
	 * @param chuti el numero de creditos, positivo para añadir, negativo para sustraer
	 * @throws NotEnoughChuti lanza esto si no hay suficientes creditos en la substraccion 
	 */
	public void setChutiGoles(User usr, Double chuti) throws NotEnoughChuti {
		User us = db.find(User.class, usr.getDNI());
		if (us != null) {
			db.getTransaction().begin();
			if (chuti<0) {
				if (us.getChutiGoles()<(-chuti)) {
					throw new NotEnoughChuti();
				}
			}
			us.setChutiGoles(us.getChutiGoles() + chuti);
			db.getTransaction().commit();
			return;
		}

	}

	/**
	 * Metodo que busca un usuario en la base de datos
	 * 
	 * @param DNI Integer que representa el Dni del usuario
	 * @return usuario Tipo user que encuentra con el mismo DNI en la base de datos
	 * @throws UserDoesntExist Excepcion para cuando el usuario no existe en la base
	 *                         de datos
	 */
	public User obtainUser(int DNI) throws UserDoesntExist {

		User us = db.find(User.class, DNI);

		if (us != null) {
			System.out.println(">> DataAccess: obtainUser=> DNI: "+us.getDNI()+" Name: " + us.getNombre());
			return us;
		}

		throw new UserDoesntExist();
	}

	/**
	 * Mï¿½todo que devuelve las preguntas de la base de datos
	 * 
	 * @param ev integer que representa el numero de evento
	 * @param q  integer que representa el numero de question
	 * @return
	 */
	public Question obtainQuestion(int ev, int q) {
		Event event = db.find(Event.class, ev);

		Vector<Question> questions = event.getQuestions();
		for (Question question : questions) {
			if (question.getQuestionNumber() == q) {
				System.out.println(">> DataAccess: obtainQuestion=>  question= " + question + " betMinimum="
						+ question.getBetMinimum());
				return question;
			}
		}
		return null;
	}

	/**
	 * Metodo que abre una base de datos en caso de que el parametro inizializeMode
	 * sea falso y en caso contrario borra la base de datos por si estuviese ya
	 * creada y crea una nueva base de datos
	 * 
	 * @param initializeMode Boolean que representa si tiene que abrir o crear una
	 *                       nueva base de datos
	 */
	public void open(boolean initializeMode) {

		System.out.println("Opening DataAccess instance => isDatabaseLocal: " + c.isDatabaseLocal()
		+ " getDatabBaseOpenMode: " + c.getDataBaseOpenMode());

		String fileName = c.getDbFilename();
		if (initializeMode) {
			fileName = fileName + ";drop";
			System.out.println("Deleting the DataBase");
		}

		if (c.isDatabaseLocal()) {
			emf = Persistence.createEntityManagerFactory("objectdb:" + fileName);
			db = emf.createEntityManager();
		} else {
			Map<String, String> properties = new HashMap<String, String>();
			properties.put("javax.persistence.jdbc.user", c.getUser());
			properties.put("javax.persistence.jdbc.password", c.getPassword());

			emf = Persistence.createEntityManagerFactory(
					"objectdb://" + c.getDatabaseNode() + ":" + c.getDatabasePort() + "/" + fileName, properties);

			db = emf.createEntityManager();
		}

	}

	/**
	 * Metodo que comprueba si la pregunta de un evento existe
	 * 
	 * @param event    Event que representa un evento donde se encueintra la
	 *                 pregunta
	 * @param question String que representa la pregunta a buscar
	 * @return Boolean true si existe y false en caso contrario
	 */
	public boolean existQuestion(Event event, String question) {
		System.out.println(">> DataAccess: existQuestion=> event= " + event + " question= " + question);
		Event ev = db.find(Event.class, event.getEventNumber());
		return ev.DoesQuestionExists(question);

	}

	/**
	 * Metodo que comprueba si existe un usuario concreto
	 * 
	 * @param user User que representa el usuario a busca
	 * @return Boolean true si existe el usuario y false en caso contrario
	 */
	public boolean existUser(User user) {

		User us = db.find(User.class, user.getDNI());
		if (us != null) {
			return true;
		}
		return false;
	}

	/**
	 * Cierra la base de datos
	 */
	public void close() {
		db.close();
		System.out.println("DataBase closed");
	}

	/**
	 * Obtiene las preguntas de un evento
	 * @param evento el evento del cual se buscan las preguntas
	 * @return el vector de preguntas
	 */
	public Vector<Question> getQuestionsFromEvent(Event evento) {

		return db.find(Event.class, evento).getQuestions();

	}

	/**
	 * Obtiene los pronosticos de una pregunta
	 * @param pregunta la pregunta de la cual se quieren lso pronosticos
	 * @return devuelve el vector de pronosticos
	 */
	public Vector<Pronosticos> getPronosticosFromQuestion(Question pregunta) {

		return db.find(Question.class, pregunta).getPronosticos();

	}

	/**
	 * Añade una apuesta 
	 * @param apuesta la apuesta a añadir
	 */
	public void addApuesta(Apuesta apuesta) {

		db.getTransaction().begin();
		User usuario= db.find(User.class, apuesta.getUser().getDNI());
		User admin = db.find(User.class, adminDNI);
		usuario.addApuesta(apuesta);
		usuario.addChutiGoles(-apuesta.getChutiGoles());
		admin.addChutiGoles(+apuesta.getChutiGoles());

		Question q = db.find(Question.class, apuesta.getQuestion().getQuestionNumber());
		Event e = db.find(Event.class, apuesta.getEvento().getEventNumber());
		q.addApuesta(apuesta);

		db.persist(e);
		db.persist(usuario);
		db.persist(admin);
		db.getTransaction().commit();
		System.out.println("apuesta realizada");
	}

	/**
	 * Se realiza el pago de una apuesta a un usuario
	 * @param us el usuario beneficiario
	 * @param chuti la cantidad a pagar
	 */
	public void pagarUsuario(User us, double chuti) {
		db.getTransaction().begin();
		User usuario= db.find(User.class, us.getDNI());
		User admin = db.find(User.class, adminDNI);

		usuario.addChutiGoles(chuti);
		admin.addChutiGoles(-chuti);

		db.persist(usuario);
		db.persist(admin);
		db.getTransaction().commit();
		System.out.println("Pagado al usuario: "+us.getDNI() + " "+ chuti+" chutiDolares");

	}

	/**
	 * Se cierran las apuestas a una pregunta
	 * @param q la pregunta de la cual se quieren cerrar las apuestas
	 * @param p los pronosticos de dicha auesta
	 */
	public void cerrarApuestaPregunta(Question q, Pronosticos p) {

		db.getTransaction().begin();
		Question qu = db.find(Question.class, q.getQuestionNumber());
		qu.setEstado(false);
		qu.setPronosticoFinal(p);
		db.persist(qu);
		db.getTransaction().commit();
		System.out.println("Pregunta: "+q.getQuestion()+" cerrada correctamente");

	}

	/**
	 * Metodo que quita la visibilidad de un evento a usuarios no privilegiados
	 * @param e el evento a cambiar su visibilidad
	 */
	public void cerrarEventoPublico(Event e) {

		db.getTransaction().begin();
		Event ev = db.find(Event.class, e.getEventNumber());
		ev.setEstadoPublico(false);
		db.persist(ev);
		db.getTransaction().commit();
		System.out.println("Evento: "+e.getDescription()+" cerrado al publico correctamente");

	}

	/**
	 * Metodo que quita la visibilidad de un evento a todos los usuarios
	 * @param e el evento a cambiar su visibilidad
	 */
	public void cerrarEvento(Event e) {

		db.getTransaction().begin();
		Event ev = db.find(Event.class, e.getEventNumber());
		ev.setEstado(false);
		db.persist(ev);
		db.getTransaction().commit();
		System.out.println("Evento: "+e.getDescription()+" cerrado correctamente");

	}

	/**
	 * Obtiene los pronosticos de una pregunta
	 * @param pregunta la pregunta de la que se quieren los pronosticos
	 * @param ganador el pronostico ganador
	 * @return devuelve los pronosticos
	 */
	public Pronosticos getPronostico(Question pregunta, String ganador) {
		Question preg = db.find(Question.class, pregunta.getQuestionNumber());
		TypedQuery<Pronosticos> query = db.createQuery("SELECT p FROM Pronosticos p WHERE p.question=?1 AND p.pronostico=?2", Pronosticos.class);
		query.setParameter(1, preg);
		query.setParameter(2, ganador);
		Pronosticos pronostico = query.getSingleResult();
		System.out.println(pronostico);
		return pronostico;

	}

	/**
	 * Encuentra un usuario 
	 * @param usuario el usuario a buscar
	 * @return el usuario encontrado
	 */
	public User findUser(User usuario) {
		return db.find(User.class, usuario.getDNI());
	}

	/**
	 * Añade un evento
	 * @param descripcion la descripcion del evento
	 * @param fecha la fecha de dicho evento
	 */
	public void addEvent(String descripcion, Date fecha, Equipo eq1, Equipo eq2) {
		db.getTransaction().begin();
		Event evento= new Event(descripcion, fecha, eq1, eq2);
		System.out.println(evento);
		db.persist(evento);
		db.getTransaction().commit();
		System.out.println("evento creado");
	}

	public Vector<Card> obtainUserCards(User us){


		User u= db.find(User.class, us.getDNI());
		System.out.println("Las tarjetas son: "+ u.getCards());
		return u.getCards();

	}

	public void addCardUser(User us, String tarjeta) {
		db.getTransaction().begin();
		User u= db.find(User.class, us.getDNI());
		Card c = new Card(tarjeta,us);
		u.addCards(c);
		db.persist(u);
		db.persist(c);
		db.getTransaction().commit();
		System.out.println("Tarjeta del usuario añadida correctamente"+u.getCards());
	}

	public Vector<Apuesta> getBetsFromUser(User us) {
		System.out.println(">> DataAccess: getBetsFromUser ");
		User us1 = db.find(User.class, us.getDNI());
		Vector<Apuesta> res = new Vector<Apuesta>();
		TypedQuery<Apuesta> query = db.createQuery("SELECT a FROM Apuesta a WHERE a.user=?1", Apuesta.class);
		query.setParameter(1, us1);
		List<Apuesta> apuestas = query.getResultList();
		for (Apuesta a : apuestas) {
			res.add(a);
		}
		return res;
	}

	public Vector<Apuesta> getBetsFromUserOpen(User us) {
		System.out.println(">> DataAccess: getBetsFromUser ");
		User us1 = db.find(User.class, us.getDNI());
		Vector<Apuesta> res = new Vector<Apuesta>();
		TypedQuery<Apuesta> query = db.createQuery("SELECT a FROM Apuesta a WHERE a.user=?1 AND a.pregunta.estado=true", Apuesta.class);
		query.setParameter(1, us1);
		List<Apuesta> apuestas = query.getResultList();
		for (Apuesta a : apuestas) {
			res.add(a);
		}
		return res;
	}
	public void cancelarApuesta(Apuesta a) {
		Apuesta ap = db.find(Apuesta.class, a.getNumeroPronostico());
		if (ap != null) {
			db.getTransaction().begin();
			Question q = db.find(Question.class, ap.getQuestion().getQuestionNumber());
			q.eliminarApuesta(ap);
			User user=db.find(User.class, a.getUser());
			user.eliminarApuesta(ap);
			User admin=db.find(User.class, adminDNI);
			admin.addChutiGoles(-a.getChutiGoles()*0.75);
			user.addChutiGoles(a.getChutiGoles()*0.75);
			db.remove(ap);
			db.persist(user);
			db.persist(admin);
			db.persist(q);
			db.getTransaction().commit();
			return;
		}

	}
	public void crearBoleto(String codigo, int max, double valor) throws NotEnoughChuti, CodigoRepetido{
		db.getTransaction().begin();
		if (db.find(Boleto.class, codigo)!=null) throw new CodigoRepetido();
		Boleto b = new Boleto(codigo, max, valor);
		User admin=db.find(User.class, adminDNI);
		if(admin.getChutiGoles()<max*valor) throw new NotEnoughChuti();
		admin.addChutiGoles(-(max*valor));
		db.persist(b);
		db.getTransaction().commit();
	}

	public void useBoleto(String codigo, User usuario) throws MaxUsed, BoletoNoExiste, BoletoUsado {
		try {
			db.getTransaction().begin();
			Boleto b = db.find(Boleto.class, codigo);
			User user = db.find(User.class, usuario);
			if (b== null) throw new BoletoNoExiste();
			if (b.getUsuarios().contains(usuario.getDNI())) throw new BoletoUsado();
			b.used();
			b.addUser(usuario.getDNI());
			user.addChutiGoles(b.getPrecio());
			db.persist(b);
			db.persist(user);
			db.getTransaction().commit();
		} catch (MaxUsed e) {
			throw new MaxUsed();
		}

	}
	public void eliminarBoleto(String codigo) throws BoletoNoExiste {
		db.getTransaction().begin();
		Boleto b = db.find(Boleto.class, codigo);
		if (b==null) throw new BoletoNoExiste();
		User admin = db.find(User.class, adminDNI);
		while(b.getUsados()<b.getMax()) {
			try {
				b.used();
				admin.addChutiGoles(b.getPrecio());
			} catch (MaxUsed e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
		db.persist(b);
		db.persist(admin);
		db.getTransaction().commit();

	}

	public void makePayment(User usr, double chutis, Date hoy, String card) {
		User us = db.find(User.class, usr.getDNI());
		Card c = db.find(Card.class, card);
		if (us != null) {
			db.getTransaction().begin();
			us.setChutiGoles(us.getChutiGoles() + chutis);
			Payment p = new Payment(us,c,chutis,hoy);
			us.addPayments(p);
			db.persist(p);
			db.persist(us);
			db.getTransaction().commit();
			return;
		}


	}

	public Vector<Payment> getPaymentsFromUser(User us) {
		System.out.println(">> DataAccess: getPaymentsFromUser ");
		User us1 = db.find(User.class, us.getDNI());
		Vector<Payment> res = new Vector<Payment>();
		TypedQuery<Payment> query = db.createQuery("SELECT p FROM Payment p WHERE  p.user=?1", Payment.class);
		query.setParameter(1, us1);
		List<Payment> pagos = query.getResultList();
		for (Payment p : pagos) {
			res.add(p);
		}
		return res;
	}


}