package nz.co.zufang.model;

import java.util.ArrayList;
import java.util.List;

public class SimilarRequest {
	
	String src;
	
	List<ArticleWeight> sampleList = new ArrayList<>();

	public String getSrc() {
		return src;
	}

	public void setSrc(String src) {
		this.src = src;
	}

	public List<ArticleWeight> getSampleList() {
		return sampleList;
	}

	public void setSampleList(List<ArticleWeight> sampleList) {
		this.sampleList = sampleList;
	}

}
