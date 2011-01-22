import gate.Gate;

public class TestGate {

	public static void main(String[] args) throws Exception {
		ClassLoader ccl = Thread.currentThread().getContextClassLoader();
		ClassLoader gcl = Gate.class.getClassLoader();
		Gate.init();
		
		
	}

}
