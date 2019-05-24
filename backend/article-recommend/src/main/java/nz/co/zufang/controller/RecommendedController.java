package nz.co.zufang.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import nz.co.zufang.model.ArticleWeight;
import nz.co.zufang.model.SimilarRequest;
import nz.co.zufang.service.JaroWinklerService;

@RestController
public class RecommendedController {

	@Autowired
	JaroWinklerService jaroWinklerService;
	@RequestMapping(value = "/infos", method = RequestMethod.POST)
	public List<ArticleWeight> getInfo(@RequestBody SimilarRequest similarRequest) {

		String src = similarRequest.getSrc();
		List<ArticleWeight> sampleList = similarRequest.getSampleList();
		return sampleList.stream().map(str -> {
			double weight = jaroWinklerService.getJaroWinklerWeight(src, str.getTitle());
			str.setWeight(weight);
			return str;
		}).<Map<String, ArticleWeight>> collect(HashMap::new,(m,e)->m.put(e.getTitle(), e), Map::putAll)
				.values().stream().sorted((ArticleWeight a, ArticleWeight b) -> {
					return b.getWeight().compareTo(a.getWeight());
					})
		   .limit(20).collect(Collectors.toList());
//		return new ArrayList<ArticleWeight>(nonDuplicatedArticleWeight);
//		.collect(collectingAndThen(toCollection(() -> new TreeSet<>(comparingInt(ArticleWeight::getTitle))),
//                                   ArrayList::new)).sorted((ArticleWeight a, ArticleWeight b) -> {
//			return b.getWeight().compareTo(a.getWeight());
//		})
//		
//		
//		Collection<Employee> nonDuplicatedEmployees = employees.stream()
//		   
//		
//		
//		.limit(20).collect(Collectors.toList());

	}
}
