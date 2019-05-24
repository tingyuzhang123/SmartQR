package nz.co.zufang.service;

import org.springframework.stereotype.Service;

@Service
public class JaroWinklerService {
	public double getJaroWinklerWeight(String src, String dist) {
		JaroWinkler jw = new JaroWinkler();
		return jw.similarity(src, dist);
	}
}
