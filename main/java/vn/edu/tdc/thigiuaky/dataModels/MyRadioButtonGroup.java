package vn.edu.tdc.thigiuaky.dataModels;

import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import java.util.ArrayList;

public class MyRadioButtonGroup {
    private  ArrayList<RadioButton> radioButtons;

    private View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            RadioButton current = ((RadioButton) view);
            for(RadioButton button : radioButtons){
                if(view.getId() != button.getId()){
                    button.setChecked(false);
                }
                else{
                    current.setChecked(true);
                }
            }
        }
    };

    public MyRadioButtonGroup(RadioButton... radioButtons){
        this.radioButtons = new ArrayList<RadioButton>();
        for(RadioButton radioButton : radioButtons){
            radioButton.setOnClickListener(onClickListener);
            this.radioButtons.add(radioButton);
        }
    }

    public String getValue(){
        for(RadioButton radioButton : radioButtons){
           if(radioButton.isChecked()) {
               return radioButton.getText().toString();
           }
        }
        return "";
    }
}
