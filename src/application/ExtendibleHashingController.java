package application;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;



public class ExtendibleHashingController {
	
	@FXML
	private Button display_hash_table;
	@FXML
	private Button search_hash_table;
	@FXML
	private  TextField gd;
	@FXML
	private  TextField ld;
	@FXML
	private  TextField bf;
	@FXML
	private  TextField no_enteries;
	@FXML
	private  TextField fill_enteries;
	@FXML
	private  TextField search_element;
	@FXML
	private Label show_table;
	@FXML
	private TextArea show_directories;
	@FXML
	private Label search_result;
	@FXML
	
	
	private String result;
	private int flag = 0;
	private String output[];
	
	
	HashMap<String, ArrayList<Integer>> bucket2;
	
   public void display_table(ActionEvent e) throws Exception{
	   
	   ExtendibleHashing obj = new ExtendibleHashing(Integer.parseInt(gd.getText()), Integer.parseInt(ld.getText()), Integer.parseInt(bf.getText()));
		if(flag == 0)
		{
			    show_directories.setText("");
			  	obj.buckets = new HashMap<String, ArrayList<Integer>>();
			  	 bucket2 = obj.buckets;
				obj.add_element(Integer.parseInt(fill_enteries.getText()));
				show_table.setText(Collections.singletonList(obj.buckets).toString());
				output = obj.show_directories().toString().split(",");
				for(int i=0; i<output.length; i++)
				{
					show_directories.appendText(output[i]);
					show_directories.appendText("\n");
				}
				
				fill_enteries.setText("");
				flag =1;
		}
		else
		{
			show_directories.setText("");
			obj.buckets = bucket2;
			obj.add_element(Integer.parseInt(fill_enteries.getText()));
			show_table.setText(Collections.singletonList(obj.buckets).toString());
			output = obj.show_directories().toString().split(",");
			for(int i=0; i<output.length; i++)
			{
				show_directories.appendText(output[i]);
				show_directories.appendText("\n");
			}
			fill_enteries.setText("");
		}	
	//System.out.println(Collections.singletonList(bucket2));
	}
   
   public void search_table(ActionEvent e)throws Exception{
	   ExtendibleHashing obj = new ExtendibleHashing(Integer.parseInt(gd.getText()), Integer.parseInt(ld.getText()), Integer.parseInt(bf.getText()));
	   obj.buckets = bucket2;
	   search_result.setText(obj.search(Integer.parseInt(search_element.getText())));
   }
   }
	
		
		
	
			 
