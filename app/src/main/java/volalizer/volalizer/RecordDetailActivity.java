package volalizer.volalizer;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class RecordDetailActivity extends AppCompatActivity {

    private boolean showSaveBtn = false;

    private Button save_btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_record_detail);

        Bundle b = getIntent().getExtras();
        showSaveBtn = b.getBoolean("showSaveBtn");

        save_btn = (Button) findViewById(R.id.save_btn);
        save_btn.setText("Save");
        if(showSaveBtn){
        save_btn.setVisibility(View.INVISIBLE);
        }else{
            save_btn.setVisibility(View.GONE);
        }


    }
}
