package ca.on.conestogac.jeopardygameapplication;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link PointsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class PointsFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private Button buttonPoints200;
    private Button buttonPoints400;
    private Button buttonPoints600;
    private Button buttonPoints800;
    private Button buttonPoints1000;

    private Button buttonNewGame;
    private Button buttonDailyDouble;

    private TextView textViewScore;
    private Button buttonSecondRound;
    private Button buttonBackToFirstRound;
    private Button buttonFinalJeopardy;

    private int totalScore;
    private final int DEFAULT_SCORE = 0;
    private View pointsFragView;

    public PointsFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment PointsFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static PointsFragment newInstance(String param1, String param2) {
        PointsFragment fragment = new PointsFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        pointsFragView = inflater.inflate(R.layout.fragment_points, container, false);

        View.OnClickListener pointsListener = new View.OnClickListener(){
            @Override
            public void onClick(View v) {

                switch (v.getId()){
                    case R.id.buttonPoints200:
                        totalScore += 200;
                        break;
                    case R.id.buttonPoints400:
                        totalScore += 400;
                        break;
                    case R.id.buttonPoints600:
                        totalScore += 200;
                        break;
                    case R.id.buttonPoints800:
                        totalScore += 200;
                        break;
                    case R.id.buttonPoints1000:
                        totalScore += 200;
                        break;
                    default:
                        break;
                }

                textViewScore.setText(String.valueOf(totalScore));
            }
        };

        View.OnClickListener newGameListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                totalScore = DEFAULT_SCORE;
                textViewScore.setText(String.valueOf(totalScore));
            }
        };
//
//        buttonPoints200 = pointsFragView.findViewById(R.id.buttonPoints200);
//        buttonPoints400 = pointsFragView.findViewById(R.id.buttonPoints400);
//        buttonPoints600 = pointsFragView.findViewById(R.id.buttonPoints600);
//        buttonPoints800 = pointsFragView.findViewById(R.id.buttonPoints800);
//        buttonPoints1000 = pointsFragView.findViewById(R.id.buttonPoints1000);
//
//        buttonNewGame = pointsFragView.findViewById(R.id.buttonNewGame);
//        buttonDailyDouble = pointsFragView.findViewById(R.id.buttonDailyDouble);
//
//        textViewScore = pointsFragView.findViewById(R.id.textViewScore);
//        buttonBackToFirstRound = pointsFragView.findViewById(R.id.buttonFirstRound);
//        buttonSecondRound = pointsFragView.findViewById(R.id.buttonSecondRound);
//        buttonFinalJeopardy = pointsFragView.findViewById(R.id.buttonFinalRound);
//
//        buttonPoints200.setOnClickListener(pointsListener);
//        buttonPoints400.setOnClickListener(pointsListener);
//        buttonPoints600.setOnClickListener(pointsListener);
//        buttonPoints800.setOnClickListener(pointsListener);
//        buttonPoints1000.setOnClickListener(pointsListener);
//
//        buttonNewGame.setOnClickListener(newGameListener);
        return pointsFragView;
    }
}