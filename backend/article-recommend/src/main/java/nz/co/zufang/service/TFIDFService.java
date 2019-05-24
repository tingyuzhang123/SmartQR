package nz.co.zufang.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.springframework.stereotype.Service;

@Service
public class TFIDFService {

	public List<List<Double>> get_tfidf(List<String> raw_data, String token) {
		Set<String> vocab = new LinkedHashSet<String>();
		Map<String, Double> word_idf = new HashMap<String, Double>();
		List<List<Double>> res = new ArrayList<List<Double>>();

		Map<String, Set<Integer>> word_docs = new HashMap<String, Set<Integer>>();
		Map<Integer, List<String>> doc_words = new HashMap<Integer, List<String>>();
		int doc_num = 0;

		for (String text : raw_data) {
			doc_num++;
			String[] words = text.split(token);
			doc_words.put(doc_num, Arrays.asList(words));
			for (String word : words) {
				vocab.add(word);
				if (word_docs.containsKey(word)) {
					word_docs.get(word).add(doc_num);
				} else {
					Set<Integer> docs = new HashSet<Integer>();
					docs.add(doc_num);
					word_docs.put(word, docs);
				}
			}
		}
		for (String word : vocab) {
			int doc_n = 0;
			if (word_docs.containsKey(word)) {
				doc_n = word_docs.get(word).size();
			}
			double idf = doc_words.size() * 1.0 / (doc_n + 1);
			word_idf.put(word, idf);
		}

		for (Entry<Integer, List<String>> e : doc_words.entrySet()) {
			List<Double> tmp = new ArrayList<Double>();
			for (String word : vocab) {
				int word_n = Collections.frequency(e.getValue(), word);
				double tf = word_n * 1.0 / e.getValue().size();
				double idf = word_idf.get(word);
				double tfidf = tf * idf;
				tmp.add(tfidf);
			}
			res.add(tmp);
		}
		return res;
	}

}
