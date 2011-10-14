package test.goodsReview.miner;

import java.util.*;


/**
 * Created by IntelliJ IDEA.
 * User: Yaroslav
 * Date: 04.10.11
 * Time: 0:18
 * To change this template use File | Settings | File Templates.
 */
public class FrequencyAnalyzer {
    private Comments comments;
	private HashMap<String,Integer> words;

    public HashMap<String,Integer> getWords() {
		return this.words;
	}

    public FrequencyAnalyzer(Comments comments) {
		this.comments = comments;
	}

    public void makeFrequencyDictionary () {
		this.words = new HashMap<String,Integer>();
		List<String> commentsList = this.comments.getComments();
		for (Iterator<String> it = commentsList.iterator(); it.hasNext();) {
			String currentComment = it.next();
			StringTokenizer st = new StringTokenizer(currentComment," ,");
			int i = 0;
			String[] listWords = new String[currentComment.length()];
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
