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

	private Untokenizer parser;
	
//	@DataPoint public static String FSP_19951228_039 = "/pt_BR/CHAVEFolha/1995/12/28/039"; // 410083452
//	@DataPoint public static String FSP_19951002_023 = "/pt_BR/CHAVEFolha/1995/10/02/023"; // 296345443
//	@DataPoint public static String FSP_19940702_058 = "/pt_BR/CHAVEFolha/1994/07/02/058"; // 262875249
//	@DataPoint public static String FSP_19940914_148 = "/pt_BR/CHAVEFolha/1994/09/14/148"; // 190509004
//	@DataPoint public static String FSP_19940702_059 = "/pt_BR/CHAVEFolha/1994/07/02/059"; // 162764716
//	@DataPoint public static String FSP_19950413_022 = "/pt_BR/CHAVEFolha/1995/04/13/022"; // 129040080
//	@DataPoint public static String FSP_19941007_089 = "/pt_BR/CHAVEFolha/1994/10/07/089"; // 126100840
//	@DataPoint public static String FSP_19940510_079 = "/pt_BR/CHAVEFolha/1994/05/10/079"; // 116757237
//	@DataPoint public static String FSP_19940430_024 = "/pt_BR/CHAVEFolha/1994/04/30/024"; // 112777188
//	@DataPoint public static String FSP_19940701_052 = "/pt_BR/CHAVEFolha/1994/07/01/052"; // 104747148
//	@DataPoint public static String FSP_19941007_091 = "/pt_BR/CHAVEFolha/1994/10/07/091"; // 102355344

	@DataPoint public static String FSP_19940101_001 = "/pt_BR/CHAVEFolha/1994/01/01/001";
	@DataPoint public static String FSP_19940101_002 = "/pt_BR/CHAVEFolha/1994/01/01/002";
	@DataPoint public static String FSP_19940101_003 = "/pt_BR/CHAVEFolha/1994/01/01/003";
	@DataPoint public static String FSP_19940101_004 = "/pt_BR/CHAVEFolha/1994/01/01/004";
	@DataPoint public static String FSP_19940101_005 = "/pt_BR/CHAVEFolha/1994/01/01/005";
	@DataPoint public static String FSP_19940101_006 = "/pt_BR/CHAVEFolha/1994/01/01/006";
	@DataPoint public static String FSP_19940101_007 = "/pt_BR/CHAVEFolha/1994/01/01/007";
	@DataPoint public static String FSP_19940101_008 = "/pt_BR/CHAVEFolha/1994/01/01/008";
	@DataPoint public static String FSP_19940101_009 = "/pt_BR/CHAVEFolha/1994/01/01/009";
	@DataPoint public static String FSP_19940101_010 = "/pt_BR/CHAVEFolha/1994/01/01/010";
	@DataPoint public static String FSP_19940101_011 = "/pt_BR/CHAVEFolha/1994/01/01/011";
	//@DataPoint public static String FSP_19940101_012 = "/pt_BR/CHAVEFolha/1994/01/01/012"; // 338-th entry: Africa=do=Sul=o'=apartheid'; buffer at 6590\nDump remaining buffer (5026): Africa do Sul o 'apartheid'
	@DataPoint public static String FSP_19940101_013 = "/pt_BR/CHAVEFolha/1994/01/01/013";
	@DataPoint public static String FSP_19940101_014 = "/pt_BR/CHAVEFolha/1994/01/01/014";
	@DataPoint public static String FSP_19940101_015 = "/pt_BR/CHAVEFolha/1994/01/01/015";
	@DataPoint public static String FSP_19940101_016 = "/pt_BR/CHAVEFolha/1994/01/01/016";
	@DataPoint public static String FSP_19940101_017 = "/pt_BR/CHAVEFolha/1994/01/01/017";
	@DataPoint public static String FSP_19940101_018 = "/pt_BR/CHAVEFolha/1994/01/01/018";
	@DataPoint public static String FSP_19940101_019 = "/pt_BR/CHAVEFolha/1994/01/01/019";
	@DataPoint public static String FSP_19940101_020 = "/pt_BR/CHAVEFolha/1994/01/01/020";
	@DataPoint public static String FSP_19940101_021 = "/pt_BR/CHAVEFolha/1994/01/01/021";
	@DataPoint public static String FSP_19940101_022 = "/pt_BR/CHAVEFolha/1994/01/01/022";
	@DataPoint public static String FSP_19940101_023 = "/pt_BR/CHAVEFolha/1994/01/01/023";
	@DataPoint public static String FSP_19940101_024 = "/pt_BR/CHAVEFolha/1994/01/01/024";
	@DataPoint public static String FSP_19940101_025 = "/pt_BR/CHAVEFolha/1994/01/01/025";
	@DataPoint public static String FSP_19940101_026 = "/pt_BR/CHAVEFolha/1994/01/01/026";
	@DataPoint public static String FSP_19940101_027 = "/pt_BR/CHAVEFolha/1994/01/01/027";
	@DataPoint public static String FSP_19940101_028 = "/pt_BR/CHAVEFolha/1994/01/01/028";
	@DataPoint public static String FSP_19940101_029 = "/pt_BR/CHAVEFolha/1994/01/01/029";
	@DataPoint public static String FSP_19940101_030 = "/pt_BR/CHAVEFolha/1994/01/01/030";
	@DataPoint public static String FSP_19940101_031 = "/pt_BR/CHAVEFolha/1994/01/01/031";
	@DataPoint public static String FSP_19940101_032 = "/pt_BR/CHAVEFolha/1994/01/01/032";
	@DataPoint public static String FSP_19940101_033 = "/pt_BR/CHAVEFolha/1994/01/01/033";
	@DataPoint public static String FSP_19940101_034 = "/pt_BR/CHAVEFolha/1994/01/01/034";
	@DataPoint public static String FSP_19940101_035 = "/pt_BR/CHAVEFolha/1994/01/01/035";
	@DataPoint public static String FSP_19940101_036 = "/pt_BR/CHAVEFolha/1994/01/01/036";
	@DataPoint public static String FSP_19940101_037 = "/pt_BR/CHAVEFolha/1994/01/01/037";
	//@DataPoint public static String FSP_19940101_038 = "/pt_BR/CHAVEFolha/1994/01/01/038"; // 220-th entry: país=o'=fuzzy ALT xxx; buffer at 1887\nDump remaining buffer (905): país o 'fuzzy logic', sistema
	//@DataPoint public static String FSP_19940101_039 = "/pt_BR/CHAVEFolha/1994/01/01/039"; //1351-th entry: criará-; Dump remaining buffer: criar-se-á imensa expectativa de q
	@DataPoint public static String FSP_19940101_040 = "/pt_BR/CHAVEFolha/1994/01/01/040";
	@DataPoint public static String FSP_19940101_041 = "/pt_BR/CHAVEFolha/1994/01/01/041";
	@DataPoint public static String FSP_19940101_042 = "/pt_BR/CHAVEFolha/1994/01/01/042";
	@DataPoint public static String FSP_19940101_043 = "/pt_BR/CHAVEFolha/1994/01/01/043";
	@DataPoint public static String FSP_19940101_044 = "/pt_BR/CHAVEFolha/1994/01/01/044";
	@DataPoint public static String FSP_19940101_045 = "/pt_BR/CHAVEFolha/1994/01/01/045";
	@DataPoint public static String FSP_19940101_046 = "/pt_BR/CHAVEFolha/1994/01/01/046";
	@DataPoint public static String FSP_19940101_047 = "/pt_BR/CHAVEFolha/1994/01/01/047";
	@DataPoint public static String FSP_19940101_048 = "/pt_BR/CHAVEFolha/1994/01/01/048";
	@DataPoint public static String FSP_19940101_049 = "/pt_BR/CHAVEFolha/1994/01/01/049";
	@DataPoint public static String FSP_19940101_050 = "/pt_BR/CHAVEFolha/1994/01/01/050";
	@DataPoint public static String FSP_19940101_051 = "/pt_BR/CHAVEFolha/1994/01/01/051";
	@DataPoint public static String FSP_19940101_052 = "/pt_BR/CHAVEFolha/1994/01/01/052";
	@DataPoint public static String FSP_19940101_053 = "/pt_BR/CHAVEFolha/1994/01/01/053";
	@DataPoint public static String FSP_19940101_054 = "/pt_BR/CHAVEFolha/1994/01/01/054";
	//@DataPoint public static String FSP_19940101_055 = "/pt_BR/CHAVEFolha/1994/01/01/055"; //79-th entry: de; Dump remaining buffer: Giuliani, que é católico,
	@DataPoint public static String FSP_19940101_056 = "/pt_BR/CHAVEFolha/1994/01/01/056";
	@DataPoint public static String FSP_19940101_057 = "/pt_BR/CHAVEFolha/1994/01/01/057";
	@DataPoint public static String FSP_19940101_058 = "/pt_BR/CHAVEFolha/1994/01/01/058";
	@DataPoint public static String FSP_19940101_059 = "/pt_BR/CHAVEFolha/1994/01/01/059";
	@DataPoint public static String FSP_19940101_060 = "/pt_BR/CHAVEFolha/1994/01/01/060";
	@DataPoint public static String FSP_19940101_061 = "/pt_BR/CHAVEFolha/1994/01/01/061";
	@DataPoint public static String FSP_19940101_062 = "/pt_BR/CHAVEFolha/1994/01/01/062";
	//@DataPoint public static String FSP_19940101_063 = "/pt_BR/CHAVEFolha/1994/01/01/063"; //23-th entry: Da=Redação; Dump remaining buffer: Sua administração é considerada ruim ou péssima por 41%
	@DataPoint public static String FSP_19940101_064 = "/pt_BR/CHAVEFolha/1994/01/01/064";
	@DataPoint public static String FSP_19940101_065 = "/pt_BR/CHAVEFolha/1994/01/01/065";
	@DataPoint public static String FSP_19940101_066 = "/pt_BR/CHAVEFolha/1994/01/01/066";
	@DataPoint public static String FSP_19940101_067 = "/pt_BR/CHAVEFolha/1994/01/01/067";
	@DataPoint public static String FSP_19940101_068 = "/pt_BR/CHAVEFolha/1994/01/01/068";
	@DataPoint public static String FSP_19940101_069 = "/pt_BR/CHAVEFolha/1994/01/01/069";
	@DataPoint public static String FSP_19940101_070 = "/pt_BR/CHAVEFolha/1994/01/01/070";
	@DataPoint public static String FSP_19940101_071 = "/pt_BR/CHAVEFolha/1994/01/01/071";
	@DataPoint public static String FSP_19940101_072 = "/pt_BR/CHAVEFolha/1994/01/01/072";
	@DataPoint public static String FSP_19940101_073 = "/pt_BR/CHAVEFolha/1994/01/01/073";
	@DataPoint public static String FSP_19940101_074 = "/pt_BR/CHAVEFolha/1994/01/01/074";
	@DataPoint public static String FSP_19940101_075 = "/pt_BR/CHAVEFolha/1994/01/01/075";
	//@DataPoint public static String FSP_19940101_076 = "/pt_BR/CHAVEFolha/1994/01/01/076"; //199-th entry: Sesc=Pinheiros-Av.; Dump remaining buffer: Sesc Pinheiros- av. Rebouças, 2876. I
	@DataPoint public static String FSP_19940101_077 = "/pt_BR/CHAVEFolha/1994/01/01/077";
	@DataPoint public static String FSP_19940101_078 = "/pt_BR/CHAVEFolha/1994/01/01/078";
	@DataPoint public static String FSP_19940101_079 = "/pt_BR/CHAVEFolha/1994/01/01/079";
	@DataPoint public static String FSP_19940101_080 = "/pt_BR/CHAVEFolha/1994/01/01/080";
	//@DataPoint public static String FSP_19940101_081 = "/pt_BR/CHAVEFolha/1994/01/01/081"; //212-th entry: Folha; Dump remaining buffer: Se me frustrei é
	@DataPoint public static String FSP_19940101_082 = "/pt_BR/CHAVEFolha/1994/01/01/082";
	@DataPoint public static String FSP_19940101_083 = "/pt_BR/CHAVEFolha/1994/01/01/083";
	@DataPoint public static String FSP_19940101_084 = "/pt_BR/CHAVEFolha/1994/01/01/084";
	@DataPoint public static String FSP_19940101_085 = "/pt_BR/CHAVEFolha/1994/01/01/085";
	@DataPoint public static String FSP_19940101_086 = "/pt_BR/CHAVEFolha/1994/01/01/086";
	//@DataPoint public static String FSP_19940101_087 = "/pt_BR/CHAVEFolha/1994/01/01/087"; //67-th entry: março; Dump remaining buffer: dezembro, cerca de 80%
	@DataPoint public static String FSP_19940101_088 = "/pt_BR/CHAVEFolha/1994/01/01/088";
	@DataPoint public static String FSP_19940101_089 = "/pt_BR/CHAVEFolha/1994/01/01/089";
	@DataPoint public static String FSP_19940101_090 = "/pt_BR/CHAVEFolha/1994/01/01/090";
	@DataPoint public static String FSP_19940101_091 = "/pt_BR/CHAVEFolha/1994/01/01/091";
	@DataPoint public static String FSP_19940101_092 = "/pt_BR/CHAVEFolha/1994/01/01/092";
	@DataPoint public static String FSP_19940101_093 = "/pt_BR/CHAVEFolha/1994/01/01/093";
	//@DataPoint public static String FSP_19940101_094 = "/pt_BR/CHAVEFolha/1994/01/01/094"; //2-th entry: dÁgua ALT xxxua; Dump remaining buffer: d'Água' obteve
	@DataPoint public static String FSP_19940101_095 = "/pt_BR/CHAVEFolha/1994/01/01/095";
	@DataPoint public static String FSP_19940101_096 = "/pt_BR/CHAVEFolha/1994/01/01/096";
	@DataPoint public static String FSP_19940101_097 = "/pt_BR/CHAVEFolha/1994/01/01/097";
	@DataPoint public static String FSP_19940101_098 = "/pt_BR/CHAVEFolha/1994/01/01/098";
	//@DataPoint public static String FSP_19940101_099 = "/pt_BR/CHAVEFolha/1994/01/01/099"; //3-th entry: Brunner; Dump remaining buffer: Seu último trabalho 
	@DataPoint public static String FSP_19940101_100 = "/pt_BR/CHAVEFolha/1994/01/01/100";
	@DataPoint public static String FSP_19940101_101 = "/pt_BR/CHAVEFolha/1994/01/01/101";
	@DataPoint public static String FSP_19940101_102 = "/pt_BR/CHAVEFolha/1994/01/01/102";
	@DataPoint public static String FSP_19940101_103 = "/pt_BR/CHAVEFolha/1994/01/01/103";
	@DataPoint public static String FSP_19940101_104 = "/pt_BR/CHAVEFolha/1994/01/01/104";
	@DataPoint public static String FSP_19940101_105 = "/pt_BR/CHAVEFolha/1994/01/01/105";
	@DataPoint public static String FSP_19940101_106 = "/pt_BR/CHAVEFolha/1994/01/01/106";
	@DataPoint public static String FSP_19940101_107 = "/pt_BR/CHAVEFolha/1994/01/01/107";
	@DataPoint public static String FSP_19940101_108 = "/pt_BR/CHAVEFolha/1994/01/01/108";
	@DataPoint public static String FSP_19940101_109 = "/pt_BR/CHAVEFolha/1994/01/01/109";
	//@DataPoint public static String FSP_19940101_110 = "/pt_BR/CHAVEFolha/1994/01/01/110"; //34-th entry: Reuniu; Dump remaining buffer: As duas estrelas da
	@DataPoint public static String FSP_19940101_111 = "/pt_BR/CHAVEFolha/1994/01/01/111";
	//@DataPoint public static String FSP_19940101_112 = "/pt_BR/CHAVEFolha/1994/01/01/112"; //0-th entry: Produtor=de'Concubina; Dump remaining buffer: Produtor de 'Concubina' briga
	@DataPoint public static String FSP_19940101_113 = "/pt_BR/CHAVEFolha/1994/01/01/113";
	//@DataPoint public static String FSP_19940101_114 = "/pt_BR/CHAVEFolha/1994/01/01/114"; //80-th entry: Time=Warner=Inc.; Dump remaining buffer: - O mais poderoso grupo de comunicação
	@DataPoint public static String FSP_19940101_115 = "/pt_BR/CHAVEFolha/1994/01/01/115";
	@DataPoint public static String FSP_19940101_116 = "/pt_BR/CHAVEFolha/1994/01/01/116";
	@DataPoint public static String FSP_19940101_117 = "/pt_BR/CHAVEFolha/1994/01/01/117";
	//@DataPoint public static String FSP_19940101_118 = "/pt_BR/CHAVEFolha/1994/01/01/118"; //248-th entry: Uma=Fortuna=Perigosa=Follett; Dump remaining buffer: Uma Fortuna Perigosa" Follett 
	//@DataPoint public static String FSP_19940101_119 = "/pt_BR/CHAVEFolha/1994/01/01/119"; //293-th entry: O=Onus...>; Dump remaining buffer: O Onus...' até que
	@DataPoint public static String FSP_19940101_120 = "/pt_BR/CHAVEFolha/1994/01/01/120";
	@DataPoint public static String FSP_19940101_121 = "/pt_BR/CHAVEFolha/1994/01/01/121";
	//@DataPoint public static String FSP_19940101_122 = "/pt_BR/CHAVEFolha/1994/01/01/122"; //331-th entry: mudou; Dump remaining buffer: as mulheres se esqueceram de 
	@DataPoint public static String FSP_19940101_123 = "/pt_BR/CHAVEFolha/1994/01/01/123";
	@DataPoint public static String FSP_19940101_124 = "/pt_BR/CHAVEFolha/1994/01/01/124";
	//@DataPoint public static String FSP_19940101_125 = "/pt_BR/CHAVEFolha/1994/01/01/125"; //246-th entry: gente; Dump remaining buffer: r meia e cueca. 
	//@DataPoint public static String FSP_19940101_126 = "/pt_BR/CHAVEFolha/1994/01/01/126"; //32-th entry: repetiu=o'=boom ALT xxx; Dump remaining buffer: repetiu o 'boom' de vendas
	@DataPoint public static String FSP_19940101_127 = "/pt_BR/CHAVEFolha/1994/01/01/127";
	@DataPoint public static String FSP_19940101_128 = "/pt_BR/CHAVEFolha/1994/01/01/128";
	@DataPoint public static String FSP_19940101_129 = "/pt_BR/CHAVEFolha/1994/01/01/129";
	//@DataPoint public static String FSP_19940101_130 = "/pt_BR/CHAVEFolha/1994/01/01/130"; //232-th entry: Rosalie=Gões=Shopping; Dump remaining buffer: Rosalie Goes Shopping). 
	@DataPoint public static String FSP_19940101_131 = "/pt_BR/CHAVEFolha/1994/01/01/131";
	@DataPoint public static String FSP_19940101_132 = "/pt_BR/CHAVEFolha/1994/01/01/132";

	@Before
	public void setUp() {
		parser = new Untokenizer();
	}
	
	@Theory
	public void testCanParse(String basename) throws Exception {
		try {
			List<CGEntry> cgLines = CGEntry.loadFromReader(open(basename + ".cg"));
			
			Document document = GateLoader.load(UntokenizerUnitTest.class.getResource(basename + ".sgml"), "UTF-8");
			
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
		return open(UntokenizerUnitTest.class.getResource(res));
	}
	
	private static Reader open(URL url) throws Exception {
		return new InputStreamReader(url.openStream(), UTF8);
	}


}
