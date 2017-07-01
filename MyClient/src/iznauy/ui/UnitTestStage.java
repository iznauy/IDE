package iznauy.ui;

import java.awt.MenuBar;

import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class UnitTestStage extends Stage {
	
	@SuppressWarnings("unused")
	private String fileName;
	
	@SuppressWarnings("unused")
	private String fileType;
	
	private String program;
	
	private MenuBar menuBar;
	

	public UnitTestStage() {
		
	}

	public UnitTestStage(StageStyle style) {
		super(style);
	}
	
	public UnitTestStage(String fileName, String fileType, String rawProgram) {
		this();
		this.fileName = fileName;
		this.fileType = fileType;
		program = rawProgram;
		init();
	}
	
	public void init() {
		
	}
	

}
