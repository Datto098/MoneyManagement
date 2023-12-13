package vn.edu.tdc.thigiuaky;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import java.util.ArrayList;

import vn.edu.tdc.thigiuaky.dataModels.MyRadioButtonGroup;
import vn.edu.tdc.thigiuaky.dataModels.Person;

public class MainActivity extends AppCompatActivity {

    RadioGroup radioGroup;
    RadioButton radioButton;
    ListView listView;
    EditText edtHoTen;
    EditText edtSoThich;
    ArrayAdapter<Person> adapter;

    ArrayList<Person> people = new ArrayList<Person>();
    MyRadioButtonGroup radioButtonGroup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Get Input form layout
        edtHoTen = findViewById(R.id.edtHoTen);
        edtSoThich = findViewById(R.id.edtSoThich);
        listView = findViewById(R.id.listView);
        RadioButton radioTrungCap = findViewById(R.id.radioTrungCap);
        RadioButton radioCapDang = findViewById(R.id.radioCaoDang);
        RadioButton radioDaiHoc = findViewById(R.id.radioDaiHoc);

        radioButtonGroup = new MyRadioButtonGroup(radioTrungCap, radioCapDang, radioDaiHoc);

        //Hien thi list view
        adapter = new ArrayAdapter<Person>(this, android.R.layout.simple_list_item_1, people );
        listView.setAdapter(adapter);


        Button btnThem = findViewById(R.id.btnThem);
        Button btnThoat = findViewById(R.id.btnThoat);

//        Get data from radioButtom
        btnThem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Person person = new Person();
                String soThich = "";
                CheckBox chkDocSach = findViewById(R.id.chkDocSach);
                CheckBox chkDiLich = findViewById(R.id.chkDuLich);

                if(chkDiLich.isChecked() & chkDocSach.isChecked()){

                    soThich = chkDocSach.getText().toString() + " " + chkDiLich.getText().toString() + " " + edtSoThich.getText().toString();
                }
                else{
                    if(chkDiLich.isChecked()){
                        soThich = chkDiLich.getText().toString() + " " + edtSoThich.getText().toString();
                    }
                    else if(chkDocSach.isChecked()){
                        soThich = chkDocSach.getText().toString() + " " + edtSoThich.getText().toString();
                    }
                    else{
                        soThich = edtSoThich.getText().toString();
                    }
                }

                person.setHoTen(edtHoTen.getText().toString());
                person.setBangCap(radioButtonGroup.getValue());
                person.setSoThich(soThich);
                Log.d("test",person.toString());
                people.add(person);
            }
        });

        btnThoat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_MAIN);
                intent.addCategory(Intent.CATEGORY_HOME);
                startActivity(intent);
                finish();
            }
        });

    }

}