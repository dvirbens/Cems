package client;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import client.gui.ComputerizedGradeApproveStudentListController;
import models.StudentExecutedExam;

class StatisticsCalculationTest {

	private static List<StudentExecutedExam> executedExamStudentList;

	@BeforeEach
	void setUp() throws Exception {
		executedExamStudentList = new ArrayList<>();
		StudentExecutedExam student1 = new StudentExecutedExam("Algebra II", "80", "16/06/2021");
		StudentExecutedExam student2 = new StudentExecutedExam("Algebra II", "75", "16/06/2021");
		StudentExecutedExam student3 = new StudentExecutedExam("Algebra II", "56", "16/06/2021");
		StudentExecutedExam student4 = new StudentExecutedExam("Algebra II", "50", "16/06/2021");
		StudentExecutedExam student5 = new StudentExecutedExam("Algebra II", "94", "16/06/2021");
		executedExamStudentList.add(student1);
		executedExamStudentList.add(student2);
		executedExamStudentList.add(student3);
		executedExamStudentList.add(student4);
		executedExamStudentList.add(student5);

	}

	@AfterEach
	void tearDown() throws Exception {

	}
	
	@Test
	void getAvarageAndMedianSuccessAvarageTest() {
		ComputerizedGradeApproveStudentListController controller = new ComputerizedGradeApproveStudentListController();
		double[] avgAndMedian = controller.getAvarageAndMedian(executedExamStudentList);
		double avarage = avgAndMedian[0];
		assertEquals(avarage, 71.0);
	}

	@Test
	void getAvarageAndMedianSuccessOddMedianTest() {
		ComputerizedGradeApproveStudentListController controller = new ComputerizedGradeApproveStudentListController();
		double[] avgAndMedian = controller.getAvarageAndMedian(executedExamStudentList);
		double median = avgAndMedian[1];
		assertEquals(median, 75);
	}

	@Test
	void getAvarageAndMedianSuccessEvenMedianTest() {
		StudentExecutedExam student6 = new StudentExecutedExam("Algebra II", "100", "16/06/2021");
		executedExamStudentList.add(student6);
		ComputerizedGradeApproveStudentListController controller = new ComputerizedGradeApproveStudentListController();
		double[] avgAndMedian = controller.getAvarageAndMedian(executedExamStudentList);
		double avarage = avgAndMedian[0];
		double median = avgAndMedian[1];
		System.out.println(median);
		assertEquals(median, 77.5);
	}

	@Test
	void getAvarageAndMedianNullGradeStudentTest() {
		StudentExecutedExam nullStudent = new StudentExecutedExam("Algebra II", null, "16/06/2021");
		ComputerizedGradeApproveStudentListController controller = new ComputerizedGradeApproveStudentListController();
		executedExamStudentList.add(nullStudent);
		try {
			controller.getAvarageAndMedian(executedExamStudentList);
			assertFalse(true);
		} catch (Exception e) {
			assertTrue(true);
		}
	}

	@Test
	void getAvarageAndMedianNoStudentsTest() {
		ComputerizedGradeApproveStudentListController controller = new ComputerizedGradeApproveStudentListController();
		executedExamStudentList.clear();
		double[] avgAndMedian = controller.getAvarageAndMedian(executedExamStudentList);
		double avarage = avgAndMedian[0];
		double median = avgAndMedian[1];
		assertEquals(avarage, 0.0);
		assertEquals(median, 0.0);
	}

}
