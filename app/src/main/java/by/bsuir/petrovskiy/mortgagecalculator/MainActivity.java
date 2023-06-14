package by.bsuir.petrovskiy.mortgagecalculator;

import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    //Поле для ввода числа из SeekBar
    TextView textView2;
    TextView textView7;
    SeekBar seekBar1;
    Spinner spinner1;
    Spinner spinner2;
    //Элементы spinner1
    String[] typeNA = { "Любой", "Недвижимость", "Автотранспорт", "Бизнес", "Акции"};
    String typeN = typeNA[0];
    //Элементы spinner2
    String[] years = { "1 год", "5 лет", "10 лет", "15 лет", "20 лет"};
    String yearsZeroS = years[0];
    int yearsZeroI = 1;
    //Переменная для определения диф. и ануит. кредитов. Д=true, A=false
    Boolean da = true;
    Button button1;
    Button button2;
    Button button3;
    //Объявление editText1
    EditText editText1;

    Button button4;
    Button button5;
    Button button6;
    Button button7;
    Button button8;
    Button button9;

    @Override
    protected void onCreate (Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //Объявление seekBar1 и textView1
        seekBar1 = (SeekBar) findViewById(R.id.seekBar1);
        textView2 = (TextView) findViewById(R.id.textView2);
        //Установка интерфейса-слушателя для seekBar1
        seekBar1.setOnSeekBarChangeListener(seekBarChangeListener);
        //Установкая начального значения
        textView2.setText("0");

        //Объявление spinner1
        spinner1 = findViewById(R.id.spinner1);
        // Создаем адаптер ArrayAdapter с помощью массива строк и стандартной разметки элемета spinner
        ArrayAdapter<String> adapter1 = new ArrayAdapter(this, android.R.layout.simple_spinner_item, typeNA);
        // Определяем разметку для использования при выборе элемента
        adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Применяем адаптер к элементу spinner
        spinner1.setAdapter(adapter1);
        //Установка интерфейса-слушателя для spinner1
        spinner1.setOnItemSelectedListener(itemSelectedListener1);

        //Объявление spinner2
        spinner2 = findViewById(R.id.spinner2);
        ArrayAdapter<String> adapter2 = new ArrayAdapter(this, android.R.layout.simple_spinner_item, years);
        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner2.setAdapter(adapter2);
        spinner2.setOnItemSelectedListener(itemSelectedListener2);

        //Объявление кнопок
        button1 = (Button) findViewById(R.id.button1);
        button2 = (Button) findViewById(R.id.button2);
        button3 = (Button) findViewById(R.id.button3);

        //Объявление editText1 и TextView7, содержащий информацию о сумме кредита и процентной ставке
        editText1 = (EditText) findViewById(R.id.editText1);
        textView7 = (TextView) findViewById(R.id.textView7);
    }

    //Метод нажатия кнопок "Дифференцированный" и "Аннуитетный"
    //Смена цвета кнопок и текста при нажатии
    public void onClick1(View view) {
        if (view.getId() == R.id.button1) {
            da = true;
            button1.setBackgroundTintList(ColorStateList.valueOf(Color.BLUE));
            button1.setTextColor(Color.WHITE);
            button2.setBackgroundTintList(ColorStateList.valueOf(Color.WHITE));
            button2.setTextColor(Color.BLACK);
        }
        else {
            da = false;
            button2.setBackgroundTintList(ColorStateList.valueOf(Color.BLUE));
            button2.setTextColor(Color.WHITE);
            button1.setBackgroundTintList(ColorStateList.valueOf(Color.WHITE));
            button1.setTextColor(Color.BLACK);
            Log.d("colorbt", String.valueOf(button1.getBackgroundTintList()));
        }
    }

    //Метод нажатия на кнопку "Рассчитать"
    //Подсчёт кредита
    public void onClick2 (View view) {
        String typePay; //вид платежа
        double sum = Double.valueOf(textView2.getText().toString()); //сумма кредита
        double persent = Double.valueOf(editText1.getText().toString()); //процентная ставка
        //Дифференцированный
        if (da) {
            typePay = "Дифференцированный";
            int countMonth = yearsZeroI * 12; //количество месяцев
            double payPerMonth = sum / countMonth; //оплата в месяц
            double persentPerMonth = persent / 12 / 100; //процентная ставка в месяц
            double sumResult = payPerMonth; //сумматор итоговой суммы с процентами
            double buffSum = sum;
            double buff;
            for (int i = 1; i < countMonth; i++) {
                buff = buffSum - payPerMonth;
                buffSum = buff + ((buff) * persentPerMonth);
                payPerMonth = buffSum / (countMonth - i);
                Log.d("payPerMonth", String.valueOf(payPerMonth));
                sumResult += payPerMonth;
                Log.d("sumResult", String.valueOf(sumResult));
            }
            double midPayPerMonth = sumResult / countMonth;
            //Вывод результатов на экран
            textView7.setText("Сумма кредита: " + sum + ".\nТип недвижимости: " + typeN + ".\nСрок кредита: " + yearsZeroS
                    + ".\nСтавка: " + persent + "%.\nВид платежа: " + typePay + ".\nИтоговая сумма: " + sumResult
                    + ".\nСредняя оплата в месяц: " + midPayPerMonth + ".");
        }
        //Аннуитетный
        else {
            typePay = "Аннуитетный";
            double sumResult = sum + (sum * (yearsZeroI * persent / 100)); //Итоговая сумма
            double payment = sumResult / (yearsZeroI * 12); //Оплата в месяц
            //Итоговая сумма
            textView7.setText("Сумма кредита: " + sum + ".\nТип недвижимости: " + typeN + ".\nСрок кредита: " + yearsZeroS
                    + ".\nСтавка: " + persent + "%.\nВид платежа: " + typePay + ".\nИтоговая сумма: " + sumResult
                    + ".\nОплата в месяц: " + payment + ".");
        }
    }

    //Интерфейс-слушатель seekBar1
    private SeekBar.OnSeekBarChangeListener seekBarChangeListener = new SeekBar.OnSeekBarChangeListener() {
        //Изменение SeekBar
        @Override
        public void onProgressChanged(SeekBar seekBar1, int progress, boolean fromUser) {
            textView2.setText(String.valueOf(seekBar1.getProgress()));
        }
        //Начало изменения SeekBar
        @Override
        public void onStartTrackingTouch(SeekBar seekBar1) {

        }
        //Завершение изменения SeekBar
        @Override
        public void onStopTrackingTouch(SeekBar seekBar1) {

        }
    };

    //Интерфейс-слушатель spinner1
    AdapterView.OnItemSelectedListener itemSelectedListener1 = new AdapterView.OnItemSelectedListener() {
        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

            // Получаем выбранный объект
            String item = (String)parent.getItemAtPosition(position);
            typeN = item;
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent){
        }
    };

    //Интерфейс-слушатель spinner2
    AdapterView.OnItemSelectedListener itemSelectedListener2 = new AdapterView.OnItemSelectedListener() {
        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

            // Получаем выбранный объект
            String item = (String)parent.getItemAtPosition(position);
            yearsZeroS = item;
            switch (position)
            {
                case 0:
                    yearsZeroI = 1;
                    break;
                case 1:
                    yearsZeroI = 5;
                    break;
                case 2:
                    yearsZeroI = 10;
                    break;
                case 3:
                    yearsZeroI = 15;
                    break;
                case 4:
                    yearsZeroI = 20;
                    break;
            }
            //Toast.makeText(getApplicationContext(), String.valueOf(yearsZeroI), Toast.LENGTH_SHORT).show();
            //selection.setText(item);
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent){
        }
    };

    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        textView7.setText(savedInstanceState.getString("answer"));
        da = savedInstanceState.getBoolean("da");
        button1.setBackgroundTintList(ColorStateList.valueOf(savedInstanceState.getInt("btn1Col")));
        button1.setTextColor(savedInstanceState.getInt("bText1Col"));
        button2.setBackgroundTintList(ColorStateList.valueOf(savedInstanceState.getInt("btn2Col")));
        button2.setTextColor(savedInstanceState.getInt("bText2Col"));
    }

    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString("answer", textView7.getText().toString());
        outState.putBoolean("da", da);
        if (da) {
            outState.putInt("btn1Col", Color.BLUE);
            outState.putInt("btn2Col", Color.WHITE);
            outState.putInt("bText1Col", Color.WHITE);
            outState.putInt("bText2Col", Color.BLACK);
        }
        else {
            outState.putInt("btn2Col", Color.BLUE);
            outState.putInt("btn1Col", Color.WHITE);
            outState.putInt("bText2Col", Color.WHITE);
            outState.putInt("bText1Col", Color.BLACK);
        }
    }
}

