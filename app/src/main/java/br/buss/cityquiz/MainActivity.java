package br.buss.cityquiz;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    private ArrayList<WorldCity> worldCities = new ArrayList<>();
    private WorldCity selectedCity;
    private ImageView imageView;
    private TextView stateName, isCorrectAnswer;
    private EditText editTextAnswer;
    private ProgressBar progressBar;
    private Button nextButton, answerButton;
    private int answerCount, score;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        imageView = findViewById(R.id.imageView);
        imageView.setImageResource(R.drawable.mundo);

        stateName = findViewById(R.id.stateName);
        stateName.setText("Clique em próxima para iniciar!");

        editTextAnswer = findViewById(R.id.editTextAnswer);
        editTextAnswer.setEnabled(false);
        editTextAnswer.setHint("");

        progressBar = findViewById(R.id.progressBar);
        progressBar.setProgress(answerCount);

        nextButton = findViewById(R.id.nextButton);
        answerButton = findViewById(R.id.answerButton);
        answerButton.setEnabled(false);

        isCorrectAnswer = findViewById(R.id.isCorrectAnswer);

        createStates();
    }

    public void validateAnswer(View view) {
        if (editTextAnswer.length() == 0) {
            Toast.makeText(this, "Digite uma resposta!", Toast.LENGTH_SHORT).show();
        } else {
            if (answerCount != 0) {
                String answer = editTextAnswer.getText().toString().trim();
                if (answer.equalsIgnoreCase(selectedCity.getCityName())) {
                    score += 25;
                    isCorrectAnswer.setText("Parabéns, você acertou!");
                } else {
                    isCorrectAnswer.setText("Você errou! A cidade correta é: " + selectedCity.getCityName());
                }
            }
            answerButton.setEnabled(false);
            nextButton.setEnabled(true);
            TextView scoreView = findViewById(R.id.scoreView);
            scoreView.setText("Pontuação: " + score);
        }
    }

    public void getRandomState(View view) {
        editTextAnswer.setEnabled(true);
        editTextAnswer.setText("");
        editTextAnswer.setHint("Digite seu palpite...");

        nextButton.setEnabled(false);

        isCorrectAnswer.setText("");

        stateName.setText("Onde estou?");

        if (answerCount < 4) {
            answerButton.setEnabled(true);
            int randomIndex = new Random().nextInt(worldCities.size());
            selectedCity = worldCities.get(randomIndex);
            worldCities.remove(randomIndex);
            Picasso.get().load(selectedCity.getImagePath()).into(imageView);
            answerCount++;
            progressBar.setProgress(answerCount);
        } else if (answerCount == 4) {
            imageView.setImageResource(R.drawable.fim);
            stateName.setText("FIM");
            editTextAnswer.setEnabled(false);
            editTextAnswer.setText("Fim de Jogo...");
        } else {
            nextButton.setEnabled(false);
        }
    }

    public void createStates() {
        worldCities.add(new WorldCity("Barcelona","http://31.220.51.31/ufpr/cidades/01_barcelona.jpg"));
        worldCities.add(new WorldCity("Brasília","http://31.220.51.31/ufpr/cidades/02_brasilia.jpg"));
        worldCities.add(new WorldCity("Curitiba","http://31.220.51.31/ufpr/cidades/03_curitiba.jpg"));
        worldCities.add(new WorldCity("Las Vegas","http://31.220.51.31/ufpr/cidades/04_lasvegas.jpg"));
        worldCities.add(new WorldCity("Montreal","http://31.220.51.31/ufpr/cidades/05_montreal.jpg"));
        worldCities.add(new WorldCity("Paris","http://31.220.51.31/ufpr/cidades/06_paris.jpg"));
        worldCities.add(new WorldCity("Rio de Janeiro","http://31.220.51.31/ufpr/cidades/07_riodejaneiro.jpg"));
        worldCities.add(new WorldCity("Salvador","http://31.220.51.31/ufpr/cidades/08_salvador.jpg"));
        worldCities.add(new WorldCity("São Paulo","http://31.220.51.31/ufpr/cidades/09_saopaulo.jpg"));
        worldCities.add(new WorldCity("Tóquio","http://31.220.51.31/ufpr/cidades/10_toquio.jpg"));
    }

    public class WorldCity {
        String cityName;
        private String imagePath;

        public WorldCity(String capitalName, String imagePath) {
            this.cityName = capitalName;
            this.imagePath = imagePath;
        }

        public String getCityName() {
            return this.cityName;
        }

        public String getImagePath() {
            return this.imagePath;
        }

    }
}