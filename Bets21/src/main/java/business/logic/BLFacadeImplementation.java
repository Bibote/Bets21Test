package business.logic;


import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Vector;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.jws.WebMethod;
import javax.jws.WebService;
import javax.persistence.RollbackException;

import configuration.ConfigXML;
import dataAccess.DataAccess;
import domain.Question;
import domain.User;
import domain.Apuesta;



import domain.Card;
import domain.Equipo;
import domain.Event;
import domain.Payment;
import domain.Pronosticos;
import exceptions.BoletoNoExiste;
import exceptions.BoletoUsado;
import exceptions.CodigoRepetido;
import exceptions.DifferentEmails;
import exceptions.DifferentPasswords;
import exceptions.EmptyNames;
import exceptions.ErrorCreditCard;
import exceptions.EventFinished;
import exceptions.MaxUsed;
import exceptions.NeedMoreThan18y;
import exceptions.NoCardsStored;
import exceptions.NotEnoughChuti;
import exceptions.NotNumbersError;
import exceptions.OldDateError;
import exceptions.PasswordMustBeLarger;
import exceptions.PreferencesNotChecked;
import exceptions.PrognosticAlreadyExist;
import exceptions.QuestionAlreadyExist;
import exceptions.StringIsEmpty;
import exceptions.UserAlreadyExist;
import exceptions.UserDoesntExist;
import exceptions.WrongDNI;
import exceptions.WrongEmailPattern;
import exceptions.WrongParameters;

/**
 * It implements the business logic as a web service.
 */
@WebService(endpointInterface = "businessLogic.BLFacade")
public class BLFacadeImplementation  implements BLFacade {
	DataAccess dbManager;
	User user1;
	String ini= "initialize";
	public BLFacadeImplementation()  {		
		System.out.println("Creating BLFacadeImplementation instance");
		ConfigXML c=ConfigXML.getInstance();

		if (c.getDataBaseOpenMode().equals(ini)) {
			dbManager=new DataAccess(c.getDataBaseOpenMode().equals(ini));
			dbManager.initializeDB();
			dbManager.close();
		}

	}


	public BLFacadeImplementation(DataAccess da)  {

		System.out.println("Creating BLFacadeImplementation instance with DataAccess parameter");
		ConfigXML c=ConfigXML.getInstance();

		if (c.getDataBaseOpenMode().equals(ini)) {
			da.open(true);
			da.initializeDB();
			da.close();

		}
		dbManager=da;		
	}


	/**
	 * This method creates a question for an event, with a question text and the minimum bet
	 * 
	 * @param event to which question is added
	 * @param question text of the question
	 * @param betMinimum minimum quantity of the bet
	 * @return the created question, or null, or an exception
	 * @throws EventFinished if current data is after data of the event
	 * @throws QuestionAlreadyExist if the same question already exists for the event
	 * @throws WrongParameters 
	 */
	@WebMethod
	public Question createQuestion(Event event, String question, float betMinimum, boolean equipo) throws EventFinished, QuestionAlreadyExist, WrongParameters{

		//The minimum bed must be greater than 0
		dbManager.open(false);
		Question qry=null;


		if(new Date().compareTo(event.getEventDate())>0)
			throw new EventFinished(ResourceBundle.getBundle("Etiquetas").getString("ErrorEventHasFinished"));


		qry=dbManager.createQuestion(event,question,betMinimum, equipo);		

		dbManager.close();

		return qry;
	};

	/**
	 * This method invokes the data access to retrieve the events of a given date 
	 * 
	 * @param date in which events are retrieved
	 * @return collection of events
	 */
	@WebMethod	
	public Vector<Event> getEvents(Date date)  {
		dbManager.open(false);
		Vector<Event>  events=dbManager.getEvents(date);
		dbManager.close();
		return events;
	}


	/**
	 * This method invokes the data access to retrieve the dates a month for which there are events
	 * 
	 * @param date of the month for which days with events want to be retrieved 
	 * @return collection of dates
	 */
	@WebMethod public Vector<Date> getEventsMonth(Date date) {
		dbManager.open(false);
		Vector<Date>  dates=dbManager.getEventsMonth(date);
		dbManager.close();
		return dates;
	}


	public void close() {
		DataAccess dB4oManager=new DataAccess(false);

		dB4oManager.close();

	}

	/**
	 * This method invokes the data access to initialize the database with some events and questions.
	 * It is invoked only when the option "initialize" is declared in the tag dataBaseOpenMode of resources/config.xml file
	 */	
	@WebMethod	
	public void initializeBD(){
		dbManager.open(false);
		dbManager.initializeDB();
		dbManager.close();
	}
	@WebMethod
	public User obtainCurrentUsr() {
		dbManager.open(false);

		User usuario= dbManager.findUser(this.user1);
		System.out.println(usuario.getApuestas());
		dbManager.close();
		return usuario;
	}

	@WebMethod
	public int verifyDNI(String DNI) throws WrongDNI {

		char[] numLetra = {'T', 'R', 'W', 'A', 'G', 'M', 'Y', 'F', 'P', 'D', 'X', 'B', 'N', 'J', 'Z', 'S', 'Q', 'V', 'H', 'L', 'C', 'K', 'E'};

		if (DNI.length() == 9 && Character.isLetter(DNI.charAt(8))) {
			boolean valido = true;
			for(int i = 0; i<DNI.length()-1;i++) {
				if (!(47<DNI.codePointAt(i) && DNI.codePointAt(i) <58)) {
					valido = false;
				}
			}
			if(valido) {
				char letra = Character.toUpperCase(DNI.charAt(8));
				int numDNI = Integer.parseInt(DNI.substring(0, 8));
				int resto = numDNI % 23;
				valido = (letra == numLetra[resto]);
				if(valido) {
					return numDNI;
				}
			}

		}
		throw new WrongDNI("DNI NO VALIDO");
	}


	@WebMethod
	public void passwordVerify(String a, String b) throws DifferentPasswords {
		if (a.equals(b)) {
			return;
		}
		throw new DifferentPasswords();

	}


	@SuppressWarnings("deprecation")
	@WebMethod
	public void ageVerify(Date date) throws NeedMoreThan18y {
		if(date == null) {throw new NeedMoreThan18y();}
		Date todayMinus18 = new Date();
		todayMinus18.setYear(todayMinus18.getYear()-18);
		if (date.before(todayMinus18)) {
			return;
		}
		throw new NeedMoreThan18y();
	}


	@WebMethod
	public void checkPreferences(boolean a, boolean b) throws PreferencesNotChecked {
		if (a && b) {
			return;
		}
		throw new PreferencesNotChecked();
	}


	@WebMethod
	public void passwordLenght(String a) throws PasswordMustBeLarger {
		if (a.length()>6) {
			return;
		}
		throw new PasswordMustBeLarger();
	}


	@WebMethod
	public void createUser(User usr) throws UserAlreadyExist {
		dbManager.open(false);
		dbManager.createUser(usr);
		dbManager.close();
	}

	@WebMethod
	public User getUser(Integer dni) throws UserDoesntExist 
	{
		dbManager.open(false);
		User u = dbManager.obtainUser(dni);
		dbManager.close();
		return u;
	}

	@WebMethod
	public void deleteUser(User usr) throws UserDoesntExist 
	{
		dbManager.open(false);
		dbManager.deleteUser(usr);
		dbManager.close();
	}

	public void banUser(User usr, String message) throws UserDoesntExist 
	{
		dbManager.open(false);
		dbManager.banUser(usr, message);
		dbManager.close();
	}

	public void changeChuti(User usr, Double chuti) throws NotEnoughChuti
	{
		dbManager.open(false);
		dbManager.setChutiGoles(usr, chuti);
		dbManager.close();
	}
	
	public void changeChutiUs(Double chuti) throws NotEnoughChuti
	{
		dbManager.open(false);
		dbManager.setChutiGoles(this.user1, chuti);
		dbManager.close();
	}

	@WebMethod
	public void checkEmptyUsers(User usr) throws EmptyNames {
		if (usr.getNombre().length()>0) {
			if (usr.getApellido1().length()>0) {
				if (usr.getApellido1().length()>0) {
					return;
				}
			}
		}
		throw new EmptyNames();
	}

	@WebMethod
	public void validateEmail(String emailStr) throws WrongEmailPattern{

		Pattern VALID_EMAIL_ADDRESS_REGEX = 
				Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);
		Matcher matcher = VALID_EMAIL_ADDRESS_REGEX.matcher(emailStr);
		boolean bo = matcher.find();
		if(bo) {return;}
		throw new WrongEmailPattern();
	}


	@WebMethod
	public void emailVerify(String a, String b) throws DifferentEmails {
		if (a.equals(b)) {
			return;
		}
		throw new DifferentEmails();

	}


	//	@WebMethod
	//	public void userLogin(User usr) throws UserDoesntExist {
	//		dbManager.open(false);
	//		User us = dbManager.obtainUser(usr.getDNI());
	//		this.user1 = us;
	//		dbManager.close();
	//		
	//	}
	//	
	@WebMethod
	public void userLogin(int DNI, String passWord) throws UserDoesntExist, DifferentPasswords {
		dbManager.open(false);
		User us = dbManager.obtainUser(DNI);

		if (passWord.equals(us.getcontrasena())) {

			this.user1 = us;
			dbManager.close();
			System.out.println("New LogIn from: "+us.getDNI()+ " Name: "+us.getNombre());
			return;
		}
		throw new DifferentPasswords();

	}


	@WebMethod
	public User returnCurrentUsr() {
		return this.user1;
	}


	@WebMethod
	public void logOutUser() {
		System.out.println("New LogOut from: "+this.user1.getDNI()+ " Name: "+this.user1.getNombre());
		this.user1 = null;
	}

	@WebMethod
	public void reloadUser() {
		dbManager.open(false);
		try {
			User us = dbManager.obtainUser(this.user1.getDNI());
			this.user1 = us;
		} catch (UserDoesntExist e) {

		}	
	}

	@SuppressWarnings("deprecation")
	@WebMethod
	public Date makeDate(Date dt) {

		dt.setHours(0);
		dt.setMinutes(0);
		dt.setSeconds(0);

		return dt;
	}




	@WebMethod
	public void oldDate (Date d)throws OldDateError{
		Date today = new Date();
		today = this.makeDate(today);
		if (d.before(today)) {
			throw new OldDateError();
		}
	}


	@WebMethod
	public Event createEvent(String description, Date fecha, Equipo eq1, Equipo eq2) {
		dbManager.open(false);
		Event evento= new Event(description, fecha, eq1, eq2);
		dbManager.addEvent(description, fecha, eq1, eq2);
		dbManager.close();
		return evento;


	}


	@WebMethod
	public void deleteEvent(Event e) {
		dbManager.open(false);
		dbManager.deleteEvent(e);
		dbManager.close();
	}


	@WebMethod
	public void changeEventDescription(Event e, String s) {
		dbManager.open(false);
		dbManager.changeEventDescription(e, s);
		dbManager.close();

	}


	@WebMethod
	public int stringToInt(String s) throws NotNumbersError {
		try {

			int numero = Integer.parseInt(s);
			return numero;
		}catch (NumberFormatException e) {
			throw new NotNumbersError();
		}
	}


	@WebMethod
	public void changeEventDate(Event e, Date d) {
		dbManager.open(false);
		dbManager.changeEventDate(e, d);
		dbManager.close();
	}


	@WebMethod
	public boolean hasPrivileges() {
		return this.user1.getPrivilegiado();
	}

	@WebMethod
	public boolean nlPrivilegesUser (User u) {
		return u.getPrivilegiado();
	}
	
	
	@WebMethod
	/*
	 * 
	 */
	public Pronosticos createPrognostic(int e, Question q, String pronos, float porcen, Equipo eq) throws PrognosticAlreadyExist, WrongParameters {
		dbManager.open(false);
		Pronosticos p;
		if (eq == null) {
		p=dbManager.createPrognostic(e, q, pronos, porcen);
		}else {
			p=dbManager.createPrognosticTeam(e, q, eq, porcen);
		}
		dbManager.close();
		return p;
	}


	@WebMethod
	public Question obtainQuestion(int ev, int q) {

		dbManager.open(false);
		Question que = dbManager.obtainQuestion(ev, q);
		dbManager.close();
		return que;
	}@WebMethod
	public Vector<Question> getQuestionsFromEvent(Event evento) {
		dbManager.open(false);
		Vector<Question> preguntas= dbManager.getQuestionsFromEvent(evento);
		dbManager.close();
		return preguntas;
	}
	@WebMethod
	public Vector<Pronosticos> getPronosticosFromQuestion(Question pregunta) {
		dbManager.open(false);
		Vector<Pronosticos> pronosticos= dbManager.getPronosticosFromQuestion(pregunta);
		dbManager.close();
		return pronosticos;
	}
	@WebMethod
	public void addApuesta(Apuesta apuesta) {

		dbManager.open(false);
		dbManager.addApuesta(apuesta);
		dbManager.close();
	}
	@WebMethod
	public Pronosticos getPronostico(Question pregunta, String resultado) { 
		dbManager.open(false);
		Pronosticos pronostico = dbManager.getPronostico(pregunta, resultado.subSequence(0, resultado.indexOf('.')).toString());
		dbManager.close();
		return pronostico;
	}

	@WebMethod
	public Vector<Event> getEventsBetweenDates(Date pastDate, Date todayDate) {

		dbManager.open(false);
		Vector<Event> vec = dbManager.getEventsBetweenDates(pastDate, todayDate);
		dbManager.close();
		return vec;

	}

	public double definirResultado(Question q, Pronosticos p) {
		
		
		dbManager.open(false);
		double pagado = 0;
		for(Apuesta a : q.getApuestas()) {

			if (a.getPronostico().getPrognosticNumber().equals(p.getPrognosticNumber())) {
				double chuti = a.getChutiGoles()*p.getPorcentaje();
				dbManager.pagarUsuario(a.getUser(), a.getChutiGoles()*p.getPorcentaje());
				pagado += chuti;
			}
		}
		dbManager.cerrarApuestaPregunta(q, p);

		dbManager.close();
		return pagado;
	}


	@Override
	public void restringirEventoPublico(Event e) {

		dbManager.open(false);
		dbManager.cerrarEventoPublico(e);
		dbManager.close();

	}

	@Override
	public void restringirEvento(Event e) {

		dbManager.open(false);
		dbManager.cerrarEvento(e);
		dbManager.close();

	}


	@Override
	public Vector<Card> obtenerTarjetasUsr() throws NoCardsStored {

		dbManager.open(false);
		Vector<Card> tarjetas = dbManager.obtainUserCards(this.user1);
		dbManager.close();
		System.out.println(tarjetas);
		if (tarjetas == null) {
			throw new NoCardsStored();
		}
		return tarjetas;
	}


	@Override
	public void añadirTarjetaUsr(String tarjeta) {
		dbManager.open(false);
		dbManager.addCardUser(this.user1, tarjeta);
		dbManager.close();
	}


	@Override
	public void comprobarTarjeta(String tarjeta) throws ErrorCreditCard {

		if (tarjeta.length() == 16) {
			boolean valido = true;
			for(int i = 0; i<16;i++) {
				if (!(47<tarjeta.codePointAt(i) && tarjeta.codePointAt(i) <58)) {
					valido = false;
				}
			}
			if(valido) {
				
				return;
			}
		}
		throw new ErrorCreditCard();
	}
	
	@Override
	public Vector<Apuesta> getBetsFromUser() {
		dbManager.open(false);
		Vector<Apuesta> vec = dbManager.getBetsFromUser(user1);
		dbManager.close();
		return vec;
	}
	
	@Override
	public void cancelarApuesta(Apuesta a) {
		dbManager.open(false);
		dbManager.cancelarApuesta(a);
		dbManager.close();
	}
	
	public void crearBoleto(String codigo, int max, double valor) throws NotEnoughChuti, CodigoRepetido {
		dbManager.open(false);
		dbManager.crearBoleto(codigo,  max, valor);
		dbManager.close();
		}
	public void useBoleto(String codigo, User usuario) throws MaxUsed, BoletoNoExiste, BoletoUsado  {
		dbManager.open(false);
		
			try {
				dbManager.useBoleto(codigo, usuario);
			}
			catch(BoletoNoExiste e) {
				throw new BoletoNoExiste();
			}
			catch (MaxUsed e) {
				throw new MaxUsed();
			}	
		
	}
	public void eliminarBoleto(String codigo) throws BoletoNoExiste {
		dbManager.open(false);
		dbManager.eliminarBoleto(codigo);
		dbManager.close();
	}


	@Override
	public void makePayment(double chutis, Date hoy, String card) {
		dbManager.open(false);
		dbManager.makePayment(this.user1, chutis, hoy, card);
		dbManager.close();
	}


	@Override
	public Vector<Payment> getPaymentsFromUser() {
		dbManager.open(false);
		Vector<Payment> vec = dbManager.getPaymentsFromUser(user1);
		dbManager.close();
		return vec;
	}


	@Override
	public Vector<Equipo> getEquipoFromQuestion(int q) {
		dbManager.open(false);
		Vector<Equipo> equipos = dbManager.getEquipoFromQuestion(q);
		dbManager.close();
		return equipos;
	}
	
	@Override
	public List<Equipo> obtenerEquipos(int temporada) {
		dbManager.open(false);
		List<Equipo> equipos = dbManager.obtenerEquipos(temporada);
		dbManager.close();
		return equipos;
	}


	@Override
	public Equipo crearEquipo(String nombre, int temporada, int fundacion, String sede, int aforo, String presidente, String entrenador, String web)throws RollbackException {
		Equipo eq1 = new Equipo(nombre, temporada);
		eq1.setFundacion(fundacion);
		eq1.setSede(sede);
		eq1.setAforo(aforo);
		eq1.setPresidente(presidente);
		eq1.setEntrenador(entrenador);
		eq1.setWeb(web);
		
		dbManager.open(false);
		eq1 = dbManager.saveEquipo(eq1, false);
		dbManager.close();
		
		return eq1; 
	}


	@Override
	public Equipo editarPartidosEquipo(Equipo eq,int tipo) {
		
		dbManager.open(false);
		Equipo eq1 = dbManager.editarPartidosEquipo(eq, tipo);
		dbManager.close();
		return eq1;
	}


	@Override
	public Equipo obtenerEquipo(String equipo, int temp) {
		Equipo eq1;
		dbManager.open(false);
		eq1 = dbManager.obtenerEquipo(equipo, temp);
		dbManager.close();
		return eq1;
	}


	@Override
	public Vector<Apuesta> getBetsFromUserOpen() {
		dbManager.open(false);
		Vector<Apuesta> vec = dbManager.getBetsFromUserOpen(user1);
		dbManager.close();
		return vec;
	}
}




