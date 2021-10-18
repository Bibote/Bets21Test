package business.logic;

import java.util.Vector;
import java.util.Date;
import java.util.List;
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

import javax.jws.WebMethod;
import javax.jws.WebService;
import javax.persistence.RollbackException;


/**
 * Interface that specifies the business logic.
 */
@WebService
public interface BLFacade  {

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
	@WebMethod Question createQuestion(Event event, String question, float betMinimum, boolean equipo) throws EventFinished, QuestionAlreadyExist, WrongParameters;


	/**
	 * This method retrieves the events of a given date 
	 * 
	 * @param date in which events are retrieved
	 * @return collection of events
	 */
	@WebMethod public Vector<Event> getEvents(Date date);

	/**
	 * This method retrieves from the database the dates a month for which there are events
	 * 
	 * @param date of the month for which days with events want to be retrieved 
	 * @return collection of dates
	 */
	@WebMethod public Vector<Date> getEventsMonth(Date date);

	/**
	 * This method calls the data access to initialize the database with some events and questions.
	 * It is invoked only when the option "initialize" is declared in the tag dataBaseOpenMode of resources/config.xml file
	 */	
	@WebMethod public void initializeBD();

	/**
	 * Metodo que verifica si el DNI es correcto o no (mediante los requerimientos reales de un DNI)
	 * @param DNI String que representa un DNI 
	 * @return Integer que devuelve el DNI
	 * @throws WrongDNI Excepcion para cuando el DNI no es correcto
	 */
	@WebMethod public int verifyDNI(String DNI) throws WrongDNI;

	/**
	 * Metodo que obtiene el unsuario que esta logueado en el moemento
	 * @return User que devuelve el usuario 
	 */
	@WebMethod public User obtainCurrentUsr();

	/**
	 * Metodo que indica si el usuario es privilegiado o no lo es
	 * @return Boolean que devuelve true si el usuario es privilegiado y false en caso contrario
	 */
	@WebMethod public boolean hasPrivileges();

	/**
	 * Metodo que verifica si la contraseï¿½a es igual a la segunda contraseï¿½a
	 * @param a String que representa la primera contraseï¿½a
	 * @param b String que representa la contraseï¿½a repetida
	 * @throws DifferentPasswords Excepcion para cuando las contraseï¿½as no son iguales
	 */
	@WebMethod public void passwordVerify(String a, String b) throws DifferentPasswords;
	/**
	 * Metodo que verifica si el email es igual al email anterior
	 * @param a String que representa el primer email
	 * @param b String que representa el email repetido
	 * @throws DifferentEmails Excepcion para cuando los email no son iguales
	 */
	@WebMethod public void emailVerify(String a, String b) throws DifferentEmails;

	/**
	 * Metodo que verifica la longitud de la contraseï¿½a escrita
	 * @param a String que representa la contraseï¿½a
	 * @throws PasswordMustBeLarger Excepcion para cuando la contraseï¿½a mide menos de 7
	 */
	@WebMethod public void passwordLenght(String a) throws PasswordMustBeLarger;
	/**
	 * Metodo que verifica la fecha introducida
	 * @param date Date que representa la fecha del usuario
	 * @throws NeedMoreThan18y Excepcion para cuando la fecha indicada no cumple que es mayor de 18
	 */
	@WebMethod public void ageVerify(Date date) throws NeedMoreThan18y;

	/**
	 * Metodo que verifica que las casillas de confirmacion esten pulsadas
	 * @param a Casilla1
	 * @param b Casilla2
	 * @throws PreferencesNotChecked Devuelve si alguna no lo esta
	 */
	@WebMethod public void checkPreferences(boolean a, boolean b) throws PreferencesNotChecked;

	/**
	 * Metodo que almacena un usuario ya creado en la base de datos 
	 * @param usr User que representa el usuario a crear
	 * @throws UserAlreadyExist Excepcion para cuando el usuario ya existe dentro de la base de datos
	 */
	@WebMethod public void createUser(User usr) throws UserAlreadyExist;

	/**
	 * Metodo que elimina un usuario ya creado en la base de datos 
	 * @param usr User que representa el usuario a eliminar
	 * @throws UserDoesntExist Excepcion para cuando el usuario no existe dentro de la base de datos
	 */
	@WebMethod public void deleteUser(User usr) throws UserDoesntExist;
	
	/**
	 * Metodo que valida un email 
	 * @param emailStr String que representa el email a verificar
	 * @throws WrongEmailPattern Excepcion para cuando el emial no cumple ciertas caracteristicas 
	 * (un String, un @,un String, el . y un String)
	 */
	@WebMethod public void validateEmail(String emailStr) throws WrongEmailPattern;

	/**
	 * Metodo que comprueba que los campos de nombre y apellidos no esten sin completar
	 * @param usr User que representa el usuario a comprobar
	 * @throws EmptyNames Excepcion para cuando se ha dejado algun campo sin completar
	 */
	@WebMethod public void checkEmptyUsers(User usr) throws EmptyNames;

	/**
	 * Metodo que loguea a un usuario existente
	 * @param usr User que representa un esuario a loguear
	 * @throws UserDoesntExist Excepcion para cuando el usuario indicado no existe
	 */
	@WebMethod public void userLogin(int DNI, String passWord) throws UserDoesntExist, DifferentPasswords;
		
	/**
	 * Metodo que devuelve el usuario logueado en ese momento
	 * @return el usuario tipo User que esta logueado
	 */
	@WebMethod public User returnCurrentUsr();
	/**
	 * Metodo que realiza el log out del usuario actual
	 */
	@WebMethod public void logOutUser();

	/**
	 * Pone la fecha a cero
	 * @param dt la fecha a cambiar
	 * @return la fecha puesta a 0
	 */
	@WebMethod public Date makeDate(Date dt);


	/**
	 * Metodo que verifica si la fecha dada es menor a la fecha actual
	 * @param d Date que representa la fecha 
	 * @throws OldDateError la fecha introducida es anterior a la actual
	 */
	@WebMethod public void oldDate (Date d)throws OldDateError;

	/**
	 * Metodo que elimina un evento concreto de la base de datos
	 * @param e Event que representa un Metodo para eliminar
	 */
	@WebMethod public void deleteEvent(Event e);
	/**
	 * Metodo que cambia la descripcion de un evento
	 * @param e Event que representa el evento a cambiar
	 * @param s String que representa la nueva descripcion del evento
	 */
	@WebMethod public void changeEventDescription(Event e, String s);
	/**
	 * Metodo que transdorma un String en un Integer
	 * @param s String que representa lo que hay que transformar
	 * @return Devuelve un tipo Integer del String anterior
	 * @throws NotNumbersError Excepcion para cuando lo introducido no es un tipo String
	 */
	@WebMethod public int stringToInt(String s) throws NotNumbersError;
	/**
	 * Metodo que cambia la fecha de un evento concreto
	 * @param e Event que representa un evento concreto
	 * @param d Date que representa la fecha a la se quiere cambiar
	 */
	@WebMethod public void changeEventDate(Event e, Date d);
	/**
	 * Metodo que crea un pronostico y lo guarda en la base de datos
	 * @param e Valor que hace referencia al evento
	 * @param q Valor que hace referencia a la pregunta
	 * @param pronos String que especifica el pronostico
	 * @param porcen Float que representa el porcentage a multiplicar la aupesta
	 * @return 
	 * @throws PrognosticAlreadyExist Error si el pronostico existe
	 * @throws WrongParameters 
	 */
	@WebMethod public Pronosticos createPrognostic(int e,Question q, String pronos, float porcen, Equipo eq) throws PrognosticAlreadyExist, WrongParameters;

	/**
	 * Metodo que devuelve una pregunta de la base de datos
	 * @param ev Valor que hace referencia al evento
	 * @param q Valor que hace referencia a la pregunta
	 * @return Question q
	 */
	@WebMethod public Question obtainQuestion( int ev, int q);
	
	/**
	 * Devuelve el usuario cuyo dni se pasa por parametro
	 * @param dni el dni del usuario a buscar
	 * @return devuelve el usuario
	 * @throws UserDoesntExist si el usuario no existe
	 */
	@WebMethod public User getUser(Integer dni) throws UserDoesntExist;
	
	/**
	 * Banea a un usuario y le muestra el mensaje pasado por parámetro al intentar hacer login
	 * @param usr el usuario a banear
	 * @param message el mensaje a mostrar
	 * @throws UserDoesntExist si el usuario no existe
	 */
	@WebMethod public void banUser(User usr, String message) throws UserDoesntExist;
	
	/**
	 * Devuelve true o false si el usuario es administrador o no respectivamente
	 * @param u el usuario del cual quieres obtener si es admin
	 * @return el boleano que indica si es admin
	 */
	@WebMethod public boolean nlPrivilegesUser (User u);
	
	/**
	 * Metodo para cambiar los creditos
	 * @param usr el usuario del cual quieres cambiar los creditos
	 * @param chuti los creditos a añadir o sustraer 
	 * @throws NotEnoughChuti si no hay suficientes creditos al sustraer 
	 */
	@WebMethod public void changeChuti(User usr, Double chuti) throws NotEnoughChuti;
	
	/**
	 * Obtiene las preguntas de un evento
	 * @param evento el evento del cual quieres las preguntas
	 * @return un vector con las preguntas
	 */
	@WebMethod Vector<Question> getQuestionsFromEvent(Event evento);
	
	/**
	 * Obtiene los pronósticos de una pregunta
	 * @param pregunta la pregunta de la que quieres los pronósticos
	 * @return el vector de pronósticos
	 */
	@WebMethod Vector<Pronosticos> getPronosticosFromQuestion(Question pregunta);
	
	/**
	 * Se añade una apuesta por parte de un usuario a una pregunta
	 * @param apuesta la apuesta a añadir
	 */
	@WebMethod void addApuesta(Apuesta apuesta);
	
	/**
	 * Obtiene el pronostico de una pregunta
	 * @param pregunta la pregunta de la que se quiere el pronostico
	 * @param resultado el resultado del pronostico a buscar
	 * @return el pronostico
	 */
	@WebMethod Pronosticos getPronostico(Question pregunta, String resultado);
	
	/**
	 * Crear evento
	 * @param description descripcion del evento
	 * @param fecha fecha del evento
	 * @return devuelve el evento a crear
	 */
	@WebMethod Event createEvent(String description, Date fecha, Equipo eq1, Equipo eq2);
	
	/**
	 * Recarga el usuario que ha iniciado sesión
	 */
	@WebMethod
	public void reloadUser();
	
	/**
	 * Obtener eventos entre dos fechas
	 * @param pastDate el margen izquierdo de la fecha (la más antigua)
	 * @param todayDate  el margen derecho de la fecha (la más nueva)
	 * @return el vector de eventos entre esas fechas
	 */
	@WebMethod
	public Vector<Event> getEventsBetweenDates(Date pastDate, Date todayDate) ;
	
	/**
	 * Metodo que paga a un usuario que ha ganado una apuesta
	 * @param q la pregunta de la apuesta
	 * @param p el pronostico
	 * @return devuelve el dinero pagado
	 */
	@WebMethod
	public double definirResultado(Question q, Pronosticos p);
	
	/**
	 * Restringe la visibilidad del evento a usuarios no privilegiados
	 * @param e el evento a cambiar su visibilidad
	 */
	@WebMethod 
	public void restringirEventoPublico( Event e);
	
	/**
	 * Restringe la visibilidad del evento a todos los usuarios
	 * @param e el evento a cambiar su visibilidad
	 */
	@WebMethod 
	public void restringirEvento( Event e);
	
	/**
	 * Metodo que obtiene las tarjetas guardadas del usuario
	 * @return Vector<Integer> tarjeta de credito
	 * @throws NoCardsStored Si el usuario no tiene ninguna tarjeta almacenada
	 */
	@WebMethod
	public Vector<Card> obtenerTarjetasUsr()throws NoCardsStored;
	
	@WebMethod 
	public void añadirTarjetaUsr(String usr);
	/**
	 * Metodo que comprueba si una tarjeta es valida (tiene 16 numeros)
	 * @param tarjeta Numero a comprobar
	 * @return 
	 * @throws ErrorCreditCard  Si la tarjeta es erronea
	 */
	@WebMethod
	public void comprobarTarjeta(String tarjeta) throws ErrorCreditCard;
	
	@WebMethod
	public void changeChutiUs(Double chuti) throws NotEnoughChuti;
	
	@WebMethod
	public Vector<Apuesta> getBetsFromUser();
	
	@WebMethod
	public Vector<Apuesta> getBetsFromUserOpen();
	
	@WebMethod
	public void cancelarApuesta(Apuesta a);
	@WebMethod
	public void crearBoleto(String codigo, int max, double valor) throws NotEnoughChuti, CodigoRepetido ;
	@WebMethod 
	public void useBoleto(String codigo, User usuario) throws MaxUsed, BoletoNoExiste, BoletoUsado ;
	@WebMethod 
	public void eliminarBoleto(String codigo) throws BoletoNoExiste;

	@WebMethod
	void makePayment(double chutis, Date hoy, String card);

	@WebMethod
	public Vector<Payment> getPaymentsFromUser();
	
	@WebMethod
	public Vector<Equipo>getEquipoFromQuestion(int q);
	
	@WebMethod
	public List<Equipo> obtenerEquipos(int temporada);
	
	@WebMethod
	public Equipo crearEquipo(String nombre, int temporada, int fundacion, String sede, int aforo, String presidente, String entrenador, String web) throws RollbackException;
	
	@WebMethod
	public Equipo editarPartidosEquipo(Equipo eq, int tipo);
	
	@WebMethod
	public Equipo obtenerEquipo(String equipo, int temp );

}