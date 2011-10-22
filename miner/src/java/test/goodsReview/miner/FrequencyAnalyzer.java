package test.goodsReview.miner;

import ru.goodsReview.core.model.ListOfReviews;

import java.util.*;


/**
 * Created by IntelliJ IDEA.
 * User: Yaroslav
 * Date: 04.10.11
 * Time: 0:18
 * To change this template use File | Settings | File Templates.
 */
public class FrequencyAnalyzer {
    private ListOfReviews listOfReviews;
	private HashMap<String,Integer> words;

    public HashMap<String,Integer> getWords() {
		return this.words;
	}

    public FrequencyAnalyzer(ListOfReviews listOfReviews) {
		this.listOfReviews = listOfReviews;
	}

    public void printFrequencyDictionary(){
        Iterator iterator = words.entrySet().iterator();
        while(iterator.hasNext()){
            System.out.println(iterator.next());
        }

    }

    public void makeFrequencyDictionary () {
		this.words = new HashMap<String,Integer>();
		List<String> listOfReviews = this.listOfReviews.getListOfComments();
		for (Iterator<String> it = listOfReviews.iterator(); it.hasNext();) {
			String currentCitilinkReview = it.next();
			StringTokenizer st = new StringTokenizer(currentCitilinkReview," ,");
			int i = 0;
			String[] listWords = new String[currentCitilinkReview.length()];
			while (st.hasMoreTokens()) {
				listWords[i++] = st.nextToken();
			}
			listWords = Arrays.copyOf(listWords, i);
			int length = listWords.length;
			for (int j = 0; j < length; j++)
			{
				String currentWord = listWords[j];
				if (this.words.containsKey(currentWord)) {
					this.words.put(currentWord, this.words.get(currentWord) + 1);
				} else {
					this.words.put(currentWord, 1);
				}
			}
		}
	}
}
