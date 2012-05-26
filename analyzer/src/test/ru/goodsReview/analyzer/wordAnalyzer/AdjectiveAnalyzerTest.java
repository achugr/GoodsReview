package ru.goodsreview.analyzer.wordAnalyzer;

/*
    Date: 26.11.11
    Time: 15:17
    Author: 
        Yaroslav Skudarnov 
        SkudarnovYI@gmail.com
*/

import ru.goodsreview.analyzer.util.sentence.PartOfSpeech;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.StringTokenizer;

public class AdjectiveAnalyzerTest {
    private static AdjectiveAnalyzer aa = new AdjectiveAnalyzer();
    private static WordAnalyzer aa2;

    static {
        aa2 = new WordAnalyzer() {
            @Override
            public PartOfSpeech partOfSpeech(String word) throws UnsupportedEncodingException {
                return null;  //To change body of implemented methods use File | Settings | File Templates.
            }
        };
    }

    public static void main(String[] args) throws IOException {
        String testPhrases = "Предел функции (предельное значение функции) в заданной точке, предельной для области определения функции, — такая величина, к которой стремится рассматриваемая функция при стремлении её аргумента к данной точке." +
                "Предел функции является обобщением понятия предела последовательности: изначально, под пределом функции в точке понимали предел последовательности элементов области значений функции, составленной из образов точек последовательности элементов области определения функции, сходящейся к заданной точке (предел в которой рассматривается); если такой предел существует, то говорят, что функция сходится к указанному значению; если такого предела не существует, то говорят, что функция расходится." +
                "Наиболее часто определение предела функции формулируют на языке окрестностей. То, что предел функции рассматривается только в точках, предельных для области определения функции, означает, что в каждой окрестности данной точки есть точки области определения; это позволяет говорить о стремлении аргумента функции (к данной точке). Но предельная точка области определения не обязана принадлежать самой области определения: например, можно рассматривать предел функции на концах открытого интервала, на котором определена функция (сами концы интервала в область определения не входят)." +
                "В общем случае необходимо точно указывать способ сходимости функции, для чего вводят т.н. базу подмножеств области определения функции, и тогда формулируют определение предела функции по (заданной) базе. В этом смысле система проколотых окрестностей данной точки — частный случай такой базы множеств." +
                "Поскольку на расширенной вещественной прямой можно построить базу окрестностей бесконечно удалённой точки, то оказывается допустимым описание предела функции при стремлении аргумента к бесконечности, а, также, описание ситуации, когда функция сама стремится к бесконечности (в заданной точке). Предел последовательности (как предел функции натурального аргумента), как раз предоставляет пример сходимости по базе «стремление аргумента к бесконечности»." +
                "Отсутствие предела функции (в данной точке) означает, что для любого заранее заданного значения области значений и всякой его окрестности сколь угодно близко от заданной точки существуют точки, значение функции в которых окажется за пределами заданной окрестности. Если в некоторой точке области определения функции существует предел и этот предел равен значению в данной функции, то функция оказывается непрерывной (в данной точке)." +
                "Предел функции — одно из основных понятий математического анализа.";
        StringTokenizer st = new StringTokenizer(testPhrases, " .,-—:;()\'\"\\«»");
        String currToken;

        while (st.hasMoreTokens()) {
            currToken = st.nextToken().trim();

            boolean r1 = aa.isAdjective(currToken);
            boolean r2 = aa2.partOfSpeech(currToken).equals(PartOfSpeech.ADJECTIVE);

            if (r1 != r2) {
                System.out.println("Diff results for " + currToken);
                System.out.println("Turglem: " + r1);
                System.out.println("Mystem: " + r2);
            }
//            if (aa.isAdjective(currToken)) {
//                System.out.println(currToken + " is an adjective!");
//            }
        }

        // if turglem checks partOfSpeech.size == 1
//        Diff results for изначально
//        Turglem: false
//        Mystem: true
//        Diff results for часто
//        Turglem: false
//        Mystem: true
//        Diff results for любого
//        Turglem: false
//        Mystem: true
//        Diff results for угодно
//        Turglem: false
//        Mystem: true

        // if not
//        Diff results for заданной
//        Turglem: true
//        Mystem: false
//        Diff results for данной
//        Turglem: true
//        Mystem: false
//        Diff results for заданной
//        Turglem: true
//        Mystem: false
//        Diff results for указанному
//        Turglem: true
//        Mystem: false
//        Diff results for данной
//        Turglem: true
//        Mystem: false
//        Diff results for данной
//        Turglem: true
//        Mystem: false
//        Diff results for обязана
//        Turglem: true
//        Mystem: false
//        Diff results for открытого
//        Turglem: true
//        Mystem: false
//        Diff results for необходимо
//        Turglem: true
//        Mystem: false
//        Diff results for точно
//        Turglem: true
//        Mystem: false
//        Diff results for заданной
//        Turglem: true
//        Mystem: false
//        Diff results for данной
//        Turglem: true
//        Mystem: false
//        Diff results for прямой
//        Turglem: true
//        Mystem: false
//        Diff results for бесконечно
//        Turglem: true
//        Mystem: false
//        Diff results for удалённой
//        Turglem: true
//        Mystem: false
//        Diff results for заданной
//        Turglem: true
//        Mystem: false
//        Diff results for данной
//        Turglem: true
//        Mystem: false
//        Diff results for любого
//        Turglem: false
//        Mystem: true
//        Diff results for заданного
//        Turglem: true
//        Mystem: false
//        Diff results for близко
//        Turglem: true
//        Mystem: false
//        Diff results for заданной
//        Turglem: true
//        Mystem: false
//        Diff results for заданной
//        Turglem: true
//        Mystem: false
//        Diff results for данной
//        Turglem: true
//        Mystem: false
//        Diff results for данной
//        Turglem: true
//        Mystem: false
    }
}
