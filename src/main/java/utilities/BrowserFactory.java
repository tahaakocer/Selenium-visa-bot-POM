package utilities;

import java.io.IOException;

import org.junit.After;
import org.junit.Test;

public class BrowserFactory {

	public Integer[] port;
	public String[] commandRun;
	final private String cmd = "cmd.exe";
	final private String parameter = "/c";
	final private String commandPriority1 = "wmic process where name=\"chrome.exe\" CALL setpriority realtime";
	final private String commandPriority2 = "wmic process where name=\"chromedriver.exe\" CALL setpriority realtime";
	final private String commandTaskKill = "Taskkill /f /im \"chromedriver.exe\"";

	public BrowserFactory() {
		
		port = new Integer[3];
		commandRun = new String[3];
		port[0] = 9222;
		port[1] = 9333;
		port[2] = 9444;
		commandRun[0] = "chrome.exe -remote-debugging-port=" + port[0]
				+ " --user-data-dir=\"C:\\Selenium\\Chrome_Test_Profile\"";
		commandRun[1] = "chrome.exe -remote-debugging-port=" + port[1]
				+ " --user-data-dir=\"C:\\Selenium\\Chrome3\"";
		commandRun[2] = "chrome.exe -remote-debugging-port=" + port[2]
				+ " --user-data-dir=\"C:\\Selenium\\ChromeDeneme\"";
		
		
	}

	@Test
	public void runBrowser(Integer profile) {
		ProcessBuilder debuggerBuilder;
		Process process;
		try {
			debuggerBuilder = new ProcessBuilder(cmd, parameter, commandRun[profile]);
			process = debuggerBuilder.start();
			System.out.println("chrome.exe debugger mod'ta baslatildi. port=" + port[profile]);
			WaitMethods.bekle(2);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@After
	public void setPriority() {
		ProcessBuilder priorityBuilder1, priorityBuilder2;
		Process process;
		priorityBuilder1 = new ProcessBuilder(cmd, parameter, commandPriority1);
		try {
			process = priorityBuilder1.start();
			WaitMethods.bekle(2);
			priorityBuilder2 = new ProcessBuilder(cmd, parameter, commandPriority2);
			process = priorityBuilder2.start();
		} catch (IOException e) {
			e.printStackTrace();
		}
		System.out.println("Priorty is set.");
	}

	public void taskKill() {
		ProcessBuilder processBuilder;
		Process process;
		try {
			processBuilder = new ProcessBuilder(cmd, parameter, commandTaskKill);
			process = processBuilder.start();
			System.out.println("Tum \"chromedriver.exe\" yazilimlari durduruldu.");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
