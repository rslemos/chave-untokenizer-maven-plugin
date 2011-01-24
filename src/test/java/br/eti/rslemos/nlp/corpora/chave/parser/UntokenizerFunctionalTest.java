package br.eti.rslemos.nlp.corpora.chave.parser;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;
import gate.AnnotationSet;
import gate.Document;
import gate.GateConstants;

import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.List;

import org.junit.Before;
import org.junit.experimental.theories.DataPoint;
import org.junit.experimental.theories.Theories;
import org.junit.experimental.theories.Theory;
import org.junit.runner.RunWith;

@RunWith(Theories.class)
public class UntokenizerFunctionalTest {
	
	private static final Charset UTF8 = Charset.forName("UTF-8");
	private static final URL basedir = UntokenizerUnitTest.class.getResource("/pt_BR/CHAVEFolha/1994/01/01/");

	private Untokenizer parser;
	
	@DataPoint public static String FSP_19940101_001 = "001";
	@DataPoint public static String FSP_19940101_002 = "002";
	@DataPoint public static String FSP_19940101_003 = "003";
	@DataPoint public static String FSP_19940101_004 = "004";
	@DataPoint public static String FSP_19940101_005 = "005";
	@DataPoint public static String FSP_19940101_006 = "006";
	@DataPoint public static String FSP_19940101_007 = "007";
	@DataPoint public static String FSP_19940101_008 = "008";
	@DataPoint public static String FSP_19940101_009 = "009";
	@DataPoint public static String FSP_19940101_010 = "010";
	@DataPoint public static String FSP_19940101_011 = "011";
	//@DataPoint public static String FSP_19940101_012 = "012"; // 338-th entry: Africa=do=Sul=o'=apartheid'; buffer at 6590\nDump remaining buffer (5026): Africa do Sul o 'apartheid'
	@DataPoint public static String FSP_19940101_013 = "013";
	@DataPoint public static String FSP_19940101_014 = "014";
	@DataPoint public static String FSP_19940101_015 = "015";
	@DataPoint public static String FSP_19940101_016 = "016";
	@DataPoint public static String FSP_19940101_017 = "017";
	@DataPoint public static String FSP_19940101_018 = "018";
	@DataPoint public static String FSP_19940101_019 = "019";
	@DataPoint public static String FSP_19940101_020 = "020";
	@DataPoint public static String FSP_19940101_021 = "021";
	@DataPoint public static String FSP_19940101_022 = "022";
	@DataPoint public static String FSP_19940101_023 = "023";
	@DataPoint public static String FSP_19940101_024 = "024";
	@DataPoint public static String FSP_19940101_025 = "025";
	@DataPoint public static String FSP_19940101_026 = "026";
	@DataPoint public static String FSP_19940101_027 = "027";
	@DataPoint public static String FSP_19940101_028 = "028";
	@DataPoint public static String FSP_19940101_029 = "029";
	@DataPoint public static String FSP_19940101_030 = "030";
	@DataPoint public static String FSP_19940101_031 = "031";
	@DataPoint public static String FSP_19940101_032 = "032";
	@DataPoint public static String FSP_19940101_033 = "033";
	@DataPoint public static String FSP_19940101_034 = "034";
	@DataPoint public static String FSP_19940101_035 = "035";
	@DataPoint public static String FSP_19940101_036 = "036";
	@DataPoint public static String FSP_19940101_037 = "037";
	//@DataPoint public static String FSP_19940101_038 = "038"; // 220-th entry: país=o'=fuzzy ALT xxx; buffer at 1887\nDump remaining buffer (905): país o 'fuzzy logic', sistema
	//@DataPoint public static String FSP_19940101_039 = "039"; //1351-th entry: criará-; Dump remaining buffer: criar-se-á imensa expectativa de q
	@DataPoint public static String FSP_19940101_040 = "040";
	@DataPoint public static String FSP_19940101_041 = "041";
	@DataPoint public static String FSP_19940101_042 = "042";
	@DataPoint public static String FSP_19940101_043 = "043";
	@DataPoint public static String FSP_19940101_044 = "044";
	@DataPoint public static String FSP_19940101_045 = "045";
	@DataPoint public static String FSP_19940101_046 = "046";
	@DataPoint public static String FSP_19940101_047 = "047";
	@DataPoint public static String FSP_19940101_048 = "048";
	@DataPoint public static String FSP_19940101_049 = "049";
	@DataPoint public static String FSP_19940101_050 = "050";
	@DataPoint public static String FSP_19940101_051 = "051";
	@DataPoint public static String FSP_19940101_052 = "052";
	@DataPoint public static String FSP_19940101_053 = "053";
	@DataPoint public static String FSP_19940101_054 = "054";
	//@DataPoint public static String FSP_19940101_055 = "055"; //79-th entry: de; Dump remaining buffer: Giuliani, que é católico,
	@DataPoint public static String FSP_19940101_056 = "056";
	@DataPoint public static String FSP_19940101_057 = "057";
	@DataPoint public static String FSP_19940101_058 = "058";
	@DataPoint public static String FSP_19940101_059 = "059";
	@DataPoint public static String FSP_19940101_060 = "060";
	@DataPoint public static String FSP_19940101_061 = "061";
	@DataPoint public static String FSP_19940101_062 = "062";
	//@DataPoint public static String FSP_19940101_063 = "063"; //23-th entry: Da=Redação; Dump remaining buffer: Sua administração é considerada ruim ou péssima por 41%
	@DataPoint public static String FSP_19940101_064 = "064";
	@DataPoint public static String FSP_19940101_065 = "065";
	@DataPoint public static String FSP_19940101_066 = "066";
	@DataPoint public static String FSP_19940101_067 = "067";
	@DataPoint public static String FSP_19940101_068 = "068";
	@DataPoint public static String FSP_19940101_069 = "069";
	@DataPoint public static String FSP_19940101_070 = "070";
	@DataPoint public static String FSP_19940101_071 = "071";
	@DataPoint public static String FSP_19940101_072 = "072";
	@DataPoint public static String FSP_19940101_073 = "073";
	@DataPoint public static String FSP_19940101_074 = "074";
	@DataPoint public static String FSP_19940101_075 = "075";
	//@DataPoint public static String FSP_19940101_076 = "076"; //199-th entry: Sesc=Pinheiros-Av.; Dump remaining buffer: Sesc Pinheiros- av. Rebouças, 2876. I
	@DataPoint public static String FSP_19940101_077 = "077";
	@DataPoint public static String FSP_19940101_078 = "078";
	@DataPoint public static String FSP_19940101_079 = "079";
	@DataPoint public static String FSP_19940101_080 = "080";
	//@DataPoint public static String FSP_19940101_081 = "081"; //212-th entry: Folha; Dump remaining buffer: Se me frustrei é
	@DataPoint public static String FSP_19940101_082 = "082";
	@DataPoint public static String FSP_19940101_083 = "083";
	@DataPoint public static String FSP_19940101_084 = "084";
	@DataPoint public static String FSP_19940101_085 = "085";
	@DataPoint public static String FSP_19940101_086 = "086";
	//@DataPoint public static String FSP_19940101_087 = "087"; //67-th entry: março; Dump remaining buffer: dezembro, cerca de 80%
	@DataPoint public static String FSP_19940101_088 = "088";
	@DataPoint public static String FSP_19940101_089 = "089";
	@DataPoint public static String FSP_19940101_090 = "090";
	@DataPoint public static String FSP_19940101_091 = "091";
	@DataPoint public static String FSP_19940101_092 = "092";
	@DataPoint public static String FSP_19940101_093 = "093";
	//@DataPoint public static String FSP_19940101_094 = "094"; //2-th entry: dÁgua ALT xxxua; Dump remaining buffer: d'Água' obteve
	@DataPoint public static String FSP_19940101_095 = "095";
	@DataPoint public static String FSP_19940101_096 = "096";
	@DataPoint public static String FSP_19940101_097 = "097";
	@DataPoint public static String FSP_19940101_098 = "098";
	//@DataPoint public static String FSP_19940101_099 = "099"; //3-th entry: Brunner; Dump remaining buffer: Seu último trabalho 
	@DataPoint public static String FSP_19940101_100 = "100";
	@DataPoint public static String FSP_19940101_101 = "101";
	@DataPoint public static String FSP_19940101_102 = "102";
	@DataPoint public static String FSP_19940101_103 = "103";
	@DataPoint public static String FSP_19940101_104 = "104";
	@DataPoint public static String FSP_19940101_105 = "105";
	@DataPoint public static String FSP_19940101_106 = "106";
	@DataPoint public static String FSP_19940101_107 = "107";
	@DataPoint public static String FSP_19940101_108 = "108";
	@DataPoint public static String FSP_19940101_109 = "109";
	//@DataPoint public static String FSP_19940101_110 = "110"; //34-th entry: Reuniu; Dump remaining buffer: As duas estrelas da
	@DataPoint public static String FSP_19940101_111 = "111";
	//@DataPoint public static String FSP_19940101_112 = "112"; //0-th entry: Produtor=de'Concubina; Dump remaining buffer: Produtor de 'Concubina' briga
	@DataPoint public static String FSP_19940101_113 = "113";
	//@DataPoint public static String FSP_19940101_114 = "114"; //80-th entry: Time=Warner=Inc.; Dump remaining buffer: - O mais poderoso grupo de comunicação
	@DataPoint public static String FSP_19940101_115 = "115";
	@DataPoint public static String FSP_19940101_116 = "116";
	@DataPoint public static String FSP_19940101_117 = "117";
	//@DataPoint public static String FSP_19940101_118 = "118"; //248-th entry: Uma=Fortuna=Perigosa=Follett; Dump remaining buffer: Uma Fortuna Perigosa" Follett 
	//@DataPoint public static String FSP_19940101_119 = "119"; //293-th entry: O=Onus...>; Dump remaining buffer: O Onus...' até que
	@DataPoint public static String FSP_19940101_120 = "120";
	@DataPoint public static String FSP_19940101_121 = "121";
	//@DataPoint public static String FSP_19940101_122 = "122"; //331-th entry: mudou; Dump remaining buffer: as mulheres se esqueceram de 
	@DataPoint public static String FSP_19940101_123 = "123";
	@DataPoint public static String FSP_19940101_124 = "124";
	//@DataPoint public static String FSP_19940101_125 = "125"; //246-th entry: gente; Dump remaining buffer: r meia e cueca. 
	//@DataPoint public static String FSP_19940101_126 = "126"; //32-th entry: repetiu=o'=boom ALT xxx; Dump remaining buffer: repetiu o 'boom' de vendas
	@DataPoint public static String FSP_19940101_127 = "127";
	@DataPoint public static String FSP_19940101_128 = "128";
	@DataPoint public static String FSP_19940101_129 = "129";
	//@DataPoint public static String FSP_19940101_130 = "130"; //232-th entry: Rosalie=Gões=Shopping; Dump remaining buffer: Rosalie Goes Shopping). 
	@DataPoint public static String FSP_19940101_131 = "131";
	@DataPoint public static String FSP_19940101_132 = "132";

	@Before
	public void setUp() {
		parser = new Untokenizer();
	}
	
	@Theory
	public void testCanParse(String basename) throws Exception {
		try {
			List<CGEntry> cgLines = CGEntry.loadFromReader(open(basename + ".cg"));
			
			Document document = GateLoader.load(new URL(basedir, basename + ".sgml"), "UTF-8");
			
			parser.parse(cgLines, document);
			AnnotationSet originalMarkups = document.getAnnotations(GateConstants.ORIGINAL_MARKUPS_ANNOT_SET_NAME);
			AnnotationSet tokens = originalMarkups.get("token");
			assertThat(tokens.size(), is(equalTo(cgLines.size())));
		} catch (ParserException e) {
			e.printStackTrace();
			fail(e.toString());
		}
	}

	private static Reader open(String res) throws Exception {
		return open(new URL(basedir, res));
	}
	
	private static Reader open(URL url) throws Exception {
		return new InputStreamReader(url.openStream(), UTF8);
	}


}
