/*******************************************************************************
 * BEGIN COPYRIGHT NOTICE
 * 
 * This file is part of program "chave-untokenizer-maven-plugin"
 * Copyright 2013  Rodrigo Lemos
 * 
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 * 
 * You should have received a copy of the GNU Lesser General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 * 
 * END COPYRIGHT NOTICE
 ******************************************************************************/
package br.eti.rslemos.nlp.corpora.chave.parser;

import org.junit.Ignore;
import org.junit.Test;

@Ignore
public class UntokenizerNotWorkingDataFunctionalTest extends UntokenizerAbstractFunctionalTest {
	@Test public void test$pt_BR$CHAVEFolha$1994$01$01$012() { _(); } // 338-th entry: Africa=do=Sul=o'=apartheid'; buffer at 6590\nDump remaining buffer (5026): Africa do Sul o 'apartheid'
	@Test public void test$pt_BR$CHAVEFolha$1994$01$01$063() { _(); } //23-th entry: Da=Redação; Dump remaining buffer: Sua administração é considerada ruim ou péssima por 41%
	@Test public void test$pt_BR$CHAVEFolha$1994$01$01$081() { _(); } //212-th entry: Folha; Dump remaining buffer: Se me frustrei é
	@Test public void test$pt_BR$CHAVEFolha$1994$01$01$094() { _(); } //2-th entry: dÁgua ALT xxxua; Dump remaining buffer: d'Água' obteve
	@Test public void test$pt_BR$CHAVEFolha$1994$01$01$099() { _(); } //3-th entry: Brunner; Dump remaining buffer: Seu último trabalho 
	@Test public void test$pt_BR$CHAVEFolha$1994$01$01$110() { _(); } //34-th entry: Reuniu; Dump remaining buffer: As duas estrelas da
	@Test public void test$pt_BR$CHAVEFolha$1994$01$01$112() { _(); } //0-th entry: Produtor=de'Concubina; Dump remaining buffer: Produtor de 'Concubina' briga
	@Test public void test$pt_BR$CHAVEFolha$1994$01$01$114() { _(); } //80-th entry: Time=Warner=Inc.; Dump remaining buffer: - O mais poderoso grupo de comunicação
	@Test public void test$pt_BR$CHAVEFolha$1994$01$01$122() { _(); } //331-th entry: mudou; Dump remaining buffer: as mulheres se esqueceram de 
	
}
