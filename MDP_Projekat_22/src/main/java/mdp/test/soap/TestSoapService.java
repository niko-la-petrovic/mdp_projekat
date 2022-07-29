package mdp.test.soap;

public class TestSoapService {
	public String reverse(String str) {
		return new StringBuilder(str).reverse().toString();
	}
}
