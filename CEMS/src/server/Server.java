package server;

import static common.ModelWrapper.Operation.*;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import common.ModelWrapper;
import common.SubjectCourseCollection;
import models.Database;
import models.Exam;
import models.ExamExtension;
import models.ExamProcess;
import models.ExamQuestion;
import models.ExecutedExam;
import models.Question;
import models.User;
import models.WordFile;
import ocsf.server.AbstractServer;
import ocsf.server.ConnectionToClient;

/**
 * Server class that's handle server-client communication
 * 
 * @author Arikz ,Dvir ben simon
 *
 */
public class Server extends AbstractServer {

	/**
	 * Server event listener in order to handle events that occurred by server, send
	 * to server user interface log.
	 */
	private ServerEventListener serverListener;

	/**
	 * Store database controller instance created by the constructor, in order to
	 * make transaction with database.
	 */
	private DatabaseController databaseController;

	/**
	 * Value that hold the user, use to login and and open appropriate menu
	 */
	private static SubjectCourseCollection subjectCourseCollection;

	private static Map<String, ExamProcess> examsInProcess;

	/**
	 * Indicate if the server is connected
	 */
	private static boolean isConnected = false;

	/**
	 * Creating new server connection with port given by constructor
	 * 
	 * @param port number
	 */
	public Server(int port) {
		super(port);
	}

	/**
	 * @param logListener server event listener, in order to handle event and update
	 *                    the log
	 * @param database    details instance to create database instance
	 * @param serverPort
	 */
	public Server(ServerEventListener logListener, Database database, String serverPort) {
		super(Integer.parseInt(serverPort));
		this.serverListener = logListener;
		databaseController = new DatabaseController(database, logListener);
		examsInProcess = new HashMap<>();
	}

	/**
	 * Function calls when client send message, the client send messages throw this
	 * function.
	 * 
	 * @param msg: get the returning message from client
	 */
	@Override
	protected void handleMessageFromClient(Object msg, ConnectionToClient client) {
		ModelWrapper<?> modelWrapperFromClient = (ModelWrapper<?>) msg;
		ModelWrapper<?> modelWrapperToClient;

		switch (modelWrapperFromClient.getOperation()) {

		case GET_QUESTION_LIST:
			List<Question> questionList = databaseController.getQuestionList(null);
			modelWrapperToClient = new ModelWrapper<Question>(questionList, GET_QUESTION_LIST);
			try {
				client.sendToClient(modelWrapperToClient);
			} catch (IOException e) {
				e.printStackTrace();
			}
			break;

		case GET_QUESTION_LIST_BY_EXAM_ID:
			String examID = (String) modelWrapperFromClient.getElement();
			List<ExamQuestion> questionListByExamID = databaseController.getExamQuestionsList(examID);
			modelWrapperToClient = new ModelWrapper<ExamQuestion>(questionListByExamID, GET_QUESTION_LIST_BY_EXAM_ID);
			try {
				client.sendToClient(modelWrapperToClient);
			} catch (IOException e) {
				e.printStackTrace();
			}
			break;

		case TEST_STATISTICS:
			break;

		case START_EXAM:
			try {
				ExamProcess examProcess = (ExamProcess) modelWrapperFromClient.getElement();
				if (!examsInProcess.containsKey(examProcess.getCode())) {
					examsInProcess.put(examProcess.getCode(), examProcess);
					modelWrapperToClient = new ModelWrapper<>(START_EXAM_SUCCESS);
					client.sendToClient(modelWrapperToClient);
				} else {
					modelWrapperToClient = new ModelWrapper<>(START_EXAM_FAILD);
					client.sendToClient(modelWrapperToClient);
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
			break;

		case CLOSE_EXAM:
			String code = (String) modelWrapperFromClient.getElement();
			databaseController.closeExam(code);
			try {
				client.sendToClient(modelWrapperFromClient);
			} catch (IOException e) {
				e.printStackTrace();
			}
			break;

		case CREATE_QUESTION:
			Question question = (Question) modelWrapperFromClient.getElement();
			databaseController.saveQuestion(question);
			try {
				client.sendToClient(modelWrapperFromClient);
			} catch (IOException e) {
				e.printStackTrace();
			}
			break;

		case EXAM_EXECUTE:
			String studentID = (String) modelWrapperFromClient.getElement();
			List<ExecutedExam> testArray = databaseController.getExecutedExamListByID(studentID);
			modelWrapperToClient = new ModelWrapper<ExecutedExam>(testArray, EXAM_EXECUTE);
			try {
				client.sendToClient(modelWrapperToClient);
			} catch (IOException e) {
				e.printStackTrace();
			}
			break;

		case EXTENSION_REQUEST:
			ExamExtension examExtension = (ExamExtension) modelWrapperFromClient.getElement();
			databaseController.saveExtension(examExtension);
			try {
				client.sendToClient(modelWrapperFromClient);
			} catch (IOException e) {
				e.printStackTrace();
			}
			break;

		case OVERALL_STATISTICS:
			break;

		case GET_USER:
			@SuppressWarnings("unchecked")
			List<String> userInfo = (List<String>) modelWrapperFromClient.getElements();
			User user = databaseController.getUser(userInfo.get(0), userInfo.get(1));
			modelWrapperToClient = new ModelWrapper<>(user, GET_USER);
			try {
				client.sendToClient(modelWrapperToClient);
			} catch (IOException e) {
				e.printStackTrace();
			}
			break;

		case GET_QUESTION_LIST_BY_SUBJECT:
			String subjcet = (String) modelWrapperFromClient.getElement();
			List<Question> questionListBySubject = databaseController.getQuestionList(subjcet);
			modelWrapperToClient = new ModelWrapper<>(questionListBySubject, GET_QUESTION_LIST);
			try {
				client.sendToClient(modelWrapperToClient);
			} catch (IOException e) {
				e.printStackTrace();
			}
			break;
		case CREATE_EXAM:
			Exam newExam = (Exam) modelWrapperFromClient.getElement();
			databaseController.saveExam(newExam);
			try {
				client.sendToClient(modelWrapperFromClient);
			} catch (IOException e) {
				e.printStackTrace();
			}
			break;

		case GET_EXAMS_LIST:
			List<Exam> examList = databaseController.getExamList();
			modelWrapperToClient = new ModelWrapper<>(examList, GET_EXAMS_LIST);
			try {
				client.sendToClient(modelWrapperToClient);
			} catch (IOException e) {
				e.printStackTrace();
			}
			break;

		case GET_EXAMS_LIST_BY_SUBJECT:
			String subject = (String) modelWrapperFromClient.getElement();
			List<Exam> examListBySubject = databaseController.getExamListBySubject(subject);
			modelWrapperToClient = new ModelWrapper<>(examListBySubject, GET_EXAMS_LIST_BY_SUBJECT);
			try {
				client.sendToClient(modelWrapperToClient);
			} catch (IOException e) {
				e.printStackTrace();
			}
			break;

		case GET_EXAMS_LIST_BY_COURSE:
			String course = (String) modelWrapperFromClient.getElement();
			List<Exam> examListByCourse = databaseController.getExamListByCourse(course);
			modelWrapperToClient = new ModelWrapper<>(examListByCourse, GET_EXAMS_LIST_BY_SUBJECT);
			try {
				client.sendToClient(modelWrapperToClient);
			} catch (IOException e) {
				e.printStackTrace();
			}
			break;

		case UPLOAD_FILE:
			WordFile file = (WordFile) modelWrapperFromClient.getElement();
			databaseController.UploadFile(file);
			try {
				client.sendToClient(modelWrapperFromClient);
			} catch (IOException e) {
				e.printStackTrace();
			}
			break;

		case INSERT_STUDENT_TO_EXAM:
			// Get code and check if examID exist, if so insert student to exam in DB

			@SuppressWarnings("unchecked")
			ArrayList<String> elements = (ArrayList<String>) modelWrapperFromClient.getElements();
			studentID = elements.get(0);
			String userCode = elements.get(1);
			String type = elements.get(2);
			examID = databaseController.CheckCodeAndInsertToTest(studentID, userCode, type);
			modelWrapperToClient = new ModelWrapper<>(examID, INSERT_STUDENT_TO_EXAM);
			try {
				client.sendToClient(modelWrapperToClient);
			} catch (IOException e) {
				e.printStackTrace();
			}
			break;

		case GET_EXAM_ID:
			String examCode = (String) modelWrapperFromClient.getElement();
			examID = databaseController.GetExamID(examCode);
			modelWrapperToClient = new ModelWrapper<>(examID, GET_EXAM_ID);
			try {
				client.sendToClient(modelWrapperToClient);
			} catch (IOException e) {
				e.printStackTrace();
			}
			break;

		case GET_EXAM_BY_EXAM_ID:
			examID = (String) modelWrapperFromClient.getElement();
			Exam exam = databaseController.GetExamByExamID(examID);
			modelWrapperToClient = new ModelWrapper<>(exam, GET_EXAM_BY_EXAM_ID);
			try {
				client.sendToClient(modelWrapperToClient);
			} catch (IOException e) {
				e.printStackTrace();
			}
			break;

		default:
			break;

		}

	}

	/**
	 * Method the being called when server has been started, when the server started
	 * there is link between SQL database, indicate the server is connected and
	 * print to log.
	 */
	protected void serverStarted() {
		try {
			databaseController.connectToDatabase();
			isConnected = true;
			serverListener.printToLog("Server listening for connections on port " + getPort());
			serverListener.changeButtonStatus(isConnected);
		} catch (SQLException ex) {
			try {
				this.close();
				serverListener.printToLog("Disconnected");
				serverListener.changeButtonStatus(!isConnected);
			} catch (IOException e) {
				System.out.println("MEOW");
				e.printStackTrace();
			}
		}
	}

	/**
	 * Method the being called when server has been stopped, when the server stopped
	 * indicate the server is connected and print to log.
	 */
	protected void serverStopped() {
		isConnected = false;
		serverListener.printToLog("Server has stopped listening for connections");
		serverListener.changeButtonStatus(isConnected);
	}

	/**
	 * Print to log when new client is entered the server.
	 */
	protected void clientConnected(ConnectionToClient client) {
		serverListener.printToLog("New client connection, ip address: " + client.getInetAddress());

		subjectCourseCollection = databaseController.updateSubjectCollection(subjectCourseCollection);
		ModelWrapper<SubjectCourseCollection> modelWrapperToClient = new ModelWrapper<>(subjectCourseCollection,
				GET_SUBJECT_COURSE_LIST);
		try {
			client.sendToClient(modelWrapperToClient);
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	public static boolean isConnected() {
		return isConnected;
	}

	synchronized protected void clientDisconnected(ConnectionToClient client) {
		serverListener.printToLog("client ip address: " + client.getInetAddress() + "has disconnected from the server");
	}

	public static SubjectCourseCollection getSubjectCollection() {
		return subjectCourseCollection;
	}

	public static void setSubjectCollection(SubjectCourseCollection subjectCourseCollection) {
		Server.subjectCourseCollection = subjectCourseCollection;
	}

	public static Map<String, ExamProcess> getExamsInProcess() {
		return examsInProcess;
	}

	public static void setExamsInProcess(Map<String, ExamProcess> examsInProcess) {
		Server.examsInProcess = examsInProcess;
	}

}
