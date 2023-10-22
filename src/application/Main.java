package application;
	
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.chart.XYChart.Data;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.RadioButton;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextField;
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;


public class Main extends Application implements EventHandler<ActionEvent>{
	ArrayList<DataSet> dataSets = new ArrayList<DataSet>();
	
	TextField dataSetNameField;
	Button dataSetButton;
	
	TabPane tabPane;
	Tab mainTab;
	
	ListView<DataSet> listView;
	
	
	@Override
	public void start(Stage primaryStage) {
		try {
			BorderPane root = new BorderPane();
			
			GridPane centerPane = new GridPane();
			root.setCenter(centerPane);
			
			GridPane toolbox = new GridPane();
			toolbox.setPadding(new Insets(10, 10, 10 , 10));
			toolbox.setVgap(5);
			
			tabPane = new TabPane();
			root.setCenter(tabPane);
			mainTab = new Tab("Home");
			mainTab.setClosable(false);
			tabPane.getTabs().add(mainTab);
			
			createDataSet("Fighters");

			mainTab.setContent(listView);
			updateListView();
			
			dataSetNameField = new TextField();
			dataSetButton = new Button("Create");
			dataSetButton.setOnAction(this);
			
			
			toolbox.add(new Text("Toolbox"), 0, 0);
			toolbox.add(new Text("New Data Set"), 0, 1);
			toolbox.add(dataSetNameField, 0, 2);
			toolbox.add(dataSetButton, 1, 2);
			
			
			
			root.setLeft(toolbox);
			
			
			Scene scene = new Scene(root,1000,1000);
			//scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			primaryStage.setScene(scene);
			primaryStage.setTitle("Stats Keeper");
			primaryStage.setMaximized(true);

			primaryStage.show();
			
			System.out.println("Application started.");
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		launch(args);
	}
	
	// Event handler
	String itemStr;
	int itemVal;
	@Override
	public void handle(ActionEvent event) {
		if (event.getSource() == dataSetButton) {
			createDataSet(dataSetNameField.getText());
		}
	}
	
	// Updates list view with current dataSets values
	public void updateListView() {
		ObservableList<DataSet> observableList = FXCollections.observableArrayList(dataSets);
		listView = new ListView<DataSet>(observableList);
		mainTab.setContent(listView);
	}
	
	public void createDataSet(String name) {
		boolean exists = false;
		if (name.length() < 1)  // if the data set does not have a name
			exists = true;
		for (DataSet item: dataSets) {  // checks to make sure that set name does not already exist in dataSets
			if (item.getName().equals(name)) {
				exists = true;
			}
		}
		
		if (!exists) {  // the name does no already exist, the set can be created
			DataSet set = new DataSet(name);
			dataSets.add(set);
			updateListView();
			addTab(name, set);
			System.out.println(set.getName() + " has been added to dataSets.");
		}
		else {
			System.out.println(name + " has NOT been added to dataSets.");
		}
	}
	
	
	TextField insertItemField;
	TextField insertValueField;
	Button setItemButton;
	
	TextField xAxisField;
	Button xAxisButton;
	TextField yAxisField;
	Button yAxisButton;
	
	public void addTab(String name, DataSet dataSet) {
		// Data Series
		XYChart.Series<String, Number> series1;
		CategoryAxis xAxis;
    	NumberAxis yAxis;
		xAxis = new CategoryAxis();
		xAxis.setLabel("Category");
		yAxis = new NumberAxis();
		yAxis.setLabel("Score");
		series1 = new XYChart.Series<>();
		
		BorderPane tabRoot = new BorderPane();
		GridPane tabToolbox = new GridPane();
		BorderPane tabCenter = new BorderPane();
		
		Tab tab = new Tab(name, tabRoot);
		tab.setClosable(false);
		
		
		tabToolbox.setPadding(new Insets(10, 0, 0, 10));
		tabToolbox.setHgap(5);
		tabToolbox.setVgap(5);
		
		tabRoot.setLeft(tabToolbox);
		tabRoot.setCenter(tabCenter);
		
		insertItemField = new TextField();
		insertValueField = new TextField();
		setItemButton = new Button("Set");
		setItemButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
            	String insItemName = insertItemField.getText();
            	String insItemVal = insertValueField.getText();
            	try {
            		series1.getData().add(new Data<String, Number>(insItemName, 
            								Integer.parseInt(insItemVal)));
            		
            		DataItem item = new DataItem(insItemName, Integer.parseInt(insItemVal));
            		dataSet.getCollection().put(insItemName, item);
            		updateListView();
            		System.out.println("Set data point.");
            	} catch (Exception e) {
            		System.out.println("Error in setting data point.");
            	}
            }
        });
		
		xAxisField = new TextField();
		xAxisButton = new Button("Set");
		xAxisButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				try {
					xAxis.setLabel(xAxisField.getText());
				} catch (Exception e) {
					System.out.println("Error setting x-axis label.");
				}
			}
		});
		
		yAxisField = new TextField();
		yAxisButton = new Button("Set");
		yAxisButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent arg0) {
				try {
					yAxis.setLabel(yAxisField.getText());
				} catch (Exception e) {
					System.out.println("Error setting y-axis label.");
				}
			}
		});
		
		tabToolbox.add(new Text("Insert/Edit Item  (Item, Value)"), 0, 1);
		tabToolbox.add(insertItemField, 0, 2);
		tabToolbox.add(insertValueField, 1, 2);
		tabToolbox.add(setItemButton, 2, 2);
		
		tabToolbox.add(new Text("X-Axis Name"), 0, 3);
		tabToolbox.add(xAxisField, 0, 4);
		tabToolbox.add(xAxisButton, 1, 4);
		
		tabToolbox.add(new Text("Y-Axis Name"), 0, 5);
		tabToolbox.add(yAxisField, 0, 6);
		tabToolbox.add(yAxisButton, 1, 6);
		
		//Text barGraphText = new Text("Bar Graph");
		ToggleButton barGraphButton = new ToggleButton("Bar Graph");
		  
	    // Create a ToggleGroup
	    final ToggleGroup group = new ToggleGroup();
	  
	    // Add all ToggleButtons to a ToggleGroup
	    group.getToggles().addAll(barGraphButton);
	  
	    
	    // Create a ChangeListener for the ToggleGroup
	    group.selectedToggleProperty().addListener(
	                   new ChangeListener<Toggle>() 
	    {
	        public void changed(ObservableValue<? extends Toggle> ov,
	                    final Toggle toggle, final Toggle new_toggle)
	        {
	        	String toggleBtn = "";
	        	if (new_toggle != null)  // new_toggle is null when a button is toggled off
	        		toggleBtn = ((ToggleButton)new_toggle).getText();
	        	else if (((ToggleButton)toggle).getText() == "Bar Graph") {
	        		System.out.println("Toggled Off.");
	        		tabCenter.getChildren().remove(0);
	        	}
	        	
	            if (toggleBtn.equals("Bar Graph")) {
	            	BarChart<String, Number> barChart;
	            	//Bar Chart----------------------------------------------------------------------
	    			//Creating the Bar chart 
	    			barChart = new BarChart<>(xAxis, yAxis);
	    			barChart.setTitle(dataSet.getName());
	    			barChart.setLegendVisible(false);
	    			
	    			Iterator<Entry<String, DataItem>> it = dataSet.getCollection().entrySet().iterator();
	    			
	    			while(it.hasNext()) {
	    				System.out.println(it);
	    				it.next();
	    			}
	    			
	    			//Setting the data to bar chart        
	    			barChart.getData().add(series1);
	    			tabCenter.setCenter(barChart);
	    			
	    			//root.setCenter(barChart);
	    			//------------------------------------------------------------------------------
	            }
	            	
	        }
	    });
		
		//tabToolbox.add(barGraphText, 1, 1);
		tabToolbox.add(barGraphButton, 0, 7);

		
		//tab.setContent(barChart);
		tabPane.getTabs().add(tab);
	}
	
}
