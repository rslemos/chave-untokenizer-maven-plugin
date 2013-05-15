#!/bin/dash
copyrightNotice() cat << EOF
BEGIN COPYRIGHT NOTICE

This file is part of program "chave-untokenizer-maven-plugin"
Copyright 2013  Rodrigo Lemos

This program is free software: you can redistribute it and/or modify
it under the terms of the GNU Lesser General Public License as published by
the Free Software Foundation, either version 3 of the License, or
(at your option) any later version.

This program is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
GNU Lesser General Public License for more details.

You should have received a copy of the GNU Lesser General Public License
along with this program.  If not, see <http://www.gnu.org/licenses/>.

END COPYRIGHT NOTICE
EOF

# the following files should not have copyright notice
# (generally because they are distributed under another license)
EXCLUSIONS=`cat << EOF
COPYING
COPYING.LESSER
applylicense.sh
src/main/resources/pt_BR/CHAVEFolha/1994/05/10/079.sgml
src/main/resources/pt_BR/CHAVEFolha/1994/05/10/079.cg
src/main/resources/pt_BR/CHAVEFolha/1994/04/30/024.cg
src/main/resources/pt_BR/CHAVEFolha/1994/04/30/024.sgml
src/main/resources/pt_BR/CHAVEFolha/1994/10/07/091.cg
src/main/resources/pt_BR/CHAVEFolha/1994/10/07/089.sgml
src/main/resources/pt_BR/CHAVEFolha/1994/10/07/089.cg
src/main/resources/pt_BR/CHAVEFolha/1994/10/07/091.sgml
src/main/resources/pt_BR/CHAVEFolha/1994/07/01/052.cg
src/main/resources/pt_BR/CHAVEFolha/1994/07/01/052.sgml
src/main/resources/pt_BR/CHAVEFolha/1994/07/02/058.sgml
src/main/resources/pt_BR/CHAVEFolha/1994/07/02/059.cg
src/main/resources/pt_BR/CHAVEFolha/1994/07/02/058.cg
src/main/resources/pt_BR/CHAVEFolha/1994/07/02/059.sgml
src/main/resources/pt_BR/CHAVEFolha/1994/09/14/148.cg
src/main/resources/pt_BR/CHAVEFolha/1994/09/14/148.sgml
src/main/resources/pt_BR/CHAVEFolha/1995/04/13/022.cg
src/main/resources/pt_BR/CHAVEFolha/1995/04/13/022.sgml
src/main/resources/pt_BR/CHAVEFolha/1995/10/02/023.cg
src/main/resources/pt_BR/CHAVEFolha/1995/10/02/023.sgml
src/main/resources/pt_BR/CHAVEFolha/1995/12/28/039.sgml
src/main/resources/pt_BR/CHAVEFolha/1995/12/28/039.cg
src/test/resources/chave-untokenizer-test/src/main/resources/pt_BR/CHAVEFolha/1994/01/01/001.cg
src/test/resources/chave-untokenizer-test/src/main/resources/pt_BR/CHAVEFolha/1994/01/01/001.sgml
src/test/resources/pt_BR/CHAVEFolha/1994/01/01/099.sgml
src/test/resources/pt_BR/CHAVEFolha/1994/01/01/041.cg
src/test/resources/pt_BR/CHAVEFolha/1994/01/01/105.sgml
src/test/resources/pt_BR/CHAVEFolha/1994/01/01/090.sgml
src/test/resources/pt_BR/CHAVEFolha/1994/01/01/058.sgml
src/test/resources/pt_BR/CHAVEFolha/1994/01/01/016.cg
src/test/resources/pt_BR/CHAVEFolha/1994/01/01/075.sgml
src/test/resources/pt_BR/CHAVEFolha/1994/01/01/126.cg
src/test/resources/pt_BR/CHAVEFolha/1994/01/01/059.cg
src/test/resources/pt_BR/CHAVEFolha/1994/01/01/079.sgml
src/test/resources/pt_BR/CHAVEFolha/1994/01/01/092.sgml
src/test/resources/pt_BR/CHAVEFolha/1994/01/01/033.sgml
src/test/resources/pt_BR/CHAVEFolha/1994/01/01/029.cg
src/test/resources/pt_BR/CHAVEFolha/1994/01/01/130.cg
src/test/resources/pt_BR/CHAVEFolha/1994/01/01/115.sgml
src/test/resources/pt_BR/CHAVEFolha/1994/01/01/039.sgml
src/test/resources/pt_BR/CHAVEFolha/1994/01/01/006.cg
src/test/resources/pt_BR/CHAVEFolha/1994/01/01/099.cg
src/test/resources/pt_BR/CHAVEFolha/1994/01/01/058.cg
src/test/resources/pt_BR/CHAVEFolha/1994/01/01/121.sgml
src/test/resources/pt_BR/CHAVEFolha/1994/01/01/003.sgml
src/test/resources/pt_BR/CHAVEFolha/1994/01/01/110.cg
src/test/resources/pt_BR/CHAVEFolha/1994/01/01/015.cg
src/test/resources/pt_BR/CHAVEFolha/1994/01/01/012.cg
src/test/resources/pt_BR/CHAVEFolha/1994/01/01/044.cg
src/test/resources/pt_BR/CHAVEFolha/1994/01/01/066.sgml
src/test/resources/pt_BR/CHAVEFolha/1994/01/01/072.sgml
src/test/resources/pt_BR/CHAVEFolha/1994/01/01/017.sgml
src/test/resources/pt_BR/CHAVEFolha/1994/01/01/069.cg
src/test/resources/pt_BR/CHAVEFolha/1994/01/01/121.cg
src/test/resources/pt_BR/CHAVEFolha/1994/01/01/022.cg
src/test/resources/pt_BR/CHAVEFolha/1994/01/01/034.cg
src/test/resources/pt_BR/CHAVEFolha/1994/01/01/005.cg
src/test/resources/pt_BR/CHAVEFolha/1994/01/01/097.sgml
src/test/resources/pt_BR/CHAVEFolha/1994/01/01/003.cg
src/test/resources/pt_BR/CHAVEFolha/1994/01/01/092.cg
src/test/resources/pt_BR/CHAVEFolha/1994/01/01/018.cg
src/test/resources/pt_BR/CHAVEFolha/1994/01/01/026.sgml
src/test/resources/pt_BR/CHAVEFolha/1994/01/01/013.cg
src/test/resources/pt_BR/CHAVEFolha/1994/01/01/119.cg
src/test/resources/pt_BR/CHAVEFolha/1994/01/01/037.cg
src/test/resources/pt_BR/CHAVEFolha/1994/01/01/074.sgml
src/test/resources/pt_BR/CHAVEFolha/1994/01/01/127.sgml
src/test/resources/pt_BR/CHAVEFolha/1994/01/01/024.cg
src/test/resources/pt_BR/CHAVEFolha/1994/01/01/108.sgml
src/test/resources/pt_BR/CHAVEFolha/1994/01/01/037.sgml
src/test/resources/pt_BR/CHAVEFolha/1994/01/01/100.cg
src/test/resources/pt_BR/CHAVEFolha/1994/01/01/112.cg
src/test/resources/pt_BR/CHAVEFolha/1994/01/01/124.cg
src/test/resources/pt_BR/CHAVEFolha/1994/01/01/039.cg
src/test/resources/pt_BR/CHAVEFolha/1994/01/01/086.cg
src/test/resources/pt_BR/CHAVEFolha/1994/01/01/056.sgml
src/test/resources/pt_BR/CHAVEFolha/1994/01/01/067.sgml
src/test/resources/pt_BR/CHAVEFolha/1994/01/01/080.cg
src/test/resources/pt_BR/CHAVEFolha/1994/01/01/118.sgml
src/test/resources/pt_BR/CHAVEFolha/1994/01/01/055.sgml
src/test/resources/pt_BR/CHAVEFolha/1994/01/01/021.sgml
src/test/resources/pt_BR/CHAVEFolha/1994/01/01/086.sgml
src/test/resources/pt_BR/CHAVEFolha/1994/01/01/045.sgml
src/test/resources/pt_BR/CHAVEFolha/1994/01/01/040.sgml
src/test/resources/pt_BR/CHAVEFolha/1994/01/01/073.cg
src/test/resources/pt_BR/CHAVEFolha/1994/01/01/049.cg
src/test/resources/pt_BR/CHAVEFolha/1994/01/01/032.cg
src/test/resources/pt_BR/CHAVEFolha/1994/01/01/087.cg
src/test/resources/pt_BR/CHAVEFolha/1994/01/01/132.cg
src/test/resources/pt_BR/CHAVEFolha/1994/01/01/123.cg
src/test/resources/pt_BR/CHAVEFolha/1994/01/01/007.cg
src/test/resources/pt_BR/CHAVEFolha/1994/01/01/056.cg
src/test/resources/pt_BR/CHAVEFolha/1994/01/01/024.sgml
src/test/resources/pt_BR/CHAVEFolha/1994/01/01/107.sgml
src/test/resources/pt_BR/CHAVEFolha/1994/01/01/045.cg
src/test/resources/pt_BR/CHAVEFolha/1994/01/01/002.sgml
src/test/resources/pt_BR/CHAVEFolha/1994/01/01/093.cg
src/test/resources/pt_BR/CHAVEFolha/1994/01/01/094.sgml
src/test/resources/pt_BR/CHAVEFolha/1994/01/01/124.sgml
src/test/resources/pt_BR/CHAVEFolha/1994/01/01/091.cg
src/test/resources/pt_BR/CHAVEFolha/1994/01/01/020.cg
src/test/resources/pt_BR/CHAVEFolha/1994/01/01/082.cg
src/test/resources/pt_BR/CHAVEFolha/1994/01/01/068.cg
src/test/resources/pt_BR/CHAVEFolha/1994/01/01/076.sgml
src/test/resources/pt_BR/CHAVEFolha/1994/01/01/015.sgml
src/test/resources/pt_BR/CHAVEFolha/1994/01/01/079.cg
src/test/resources/pt_BR/CHAVEFolha/1994/01/01/018.sgml
src/test/resources/pt_BR/CHAVEFolha/1994/01/01/023.cg
src/test/resources/pt_BR/CHAVEFolha/1994/01/01/009.sgml
src/test/resources/pt_BR/CHAVEFolha/1994/01/01/098.sgml
src/test/resources/pt_BR/CHAVEFolha/1994/01/01/078.sgml
src/test/resources/pt_BR/CHAVEFolha/1994/01/01/070.sgml
src/test/resources/pt_BR/CHAVEFolha/1994/01/01/125.cg
src/test/resources/pt_BR/CHAVEFolha/1994/01/01/036.cg
src/test/resources/pt_BR/CHAVEFolha/1994/01/01/115.cg
src/test/resources/pt_BR/CHAVEFolha/1994/01/01/049.sgml
src/test/resources/pt_BR/CHAVEFolha/1994/01/01/025.cg
src/test/resources/pt_BR/CHAVEFolha/1994/01/01/103.cg
src/test/resources/pt_BR/CHAVEFolha/1994/01/01/113.cg
src/test/resources/pt_BR/CHAVEFolha/1994/01/01/032.sgml
src/test/resources/pt_BR/CHAVEFolha/1994/01/01/073.sgml
src/test/resources/pt_BR/CHAVEFolha/1994/01/01/034.sgml
src/test/resources/pt_BR/CHAVEFolha/1994/01/01/087.sgml
src/test/resources/pt_BR/CHAVEFolha/1994/01/01/031.sgml
src/test/resources/pt_BR/CHAVEFolha/1994/01/01/014.sgml
src/test/resources/pt_BR/CHAVEFolha/1994/01/01/098.cg
src/test/resources/pt_BR/CHAVEFolha/1994/01/01/050.cg
src/test/resources/pt_BR/CHAVEFolha/1994/01/01/095.cg
src/test/resources/pt_BR/CHAVEFolha/1994/01/01/061.cg
src/test/resources/pt_BR/CHAVEFolha/1994/01/01/001.cg
src/test/resources/pt_BR/CHAVEFolha/1994/01/01/071.sgml
src/test/resources/pt_BR/CHAVEFolha/1994/01/01/108.cg
src/test/resources/pt_BR/CHAVEFolha/1994/01/01/104.cg
src/test/resources/pt_BR/CHAVEFolha/1994/01/01/106.cg
src/test/resources/pt_BR/CHAVEFolha/1994/01/01/048.cg
src/test/resources/pt_BR/CHAVEFolha/1994/01/01/083.cg
src/test/resources/pt_BR/CHAVEFolha/1994/01/01/002.cg
src/test/resources/pt_BR/CHAVEFolha/1994/01/01/064.sgml
src/test/resources/pt_BR/CHAVEFolha/1994/01/01/096.sgml
src/test/resources/pt_BR/CHAVEFolha/1994/01/01/042.sgml
src/test/resources/pt_BR/CHAVEFolha/1994/01/01/122.sgml
src/test/resources/pt_BR/CHAVEFolha/1994/01/01/008.cg
src/test/resources/pt_BR/CHAVEFolha/1994/01/01/106.sgml
src/test/resources/pt_BR/CHAVEFolha/1994/01/01/027.sgml
src/test/resources/pt_BR/CHAVEFolha/1994/01/01/111.sgml
src/test/resources/pt_BR/CHAVEFolha/1994/01/01/109.sgml
src/test/resources/pt_BR/CHAVEFolha/1994/01/01/036.sgml
src/test/resources/pt_BR/CHAVEFolha/1994/01/01/057.cg
src/test/resources/pt_BR/CHAVEFolha/1994/01/01/001.sgml
src/test/resources/pt_BR/CHAVEFolha/1994/01/01/075.cg
src/test/resources/pt_BR/CHAVEFolha/1994/01/01/042.cg
src/test/resources/pt_BR/CHAVEFolha/1994/01/01/129.sgml
src/test/resources/pt_BR/CHAVEFolha/1994/01/01/119.sgml
src/test/resources/pt_BR/CHAVEFolha/1994/01/01/067.cg
src/test/resources/pt_BR/CHAVEFolha/1994/01/01/040.cg
src/test/resources/pt_BR/CHAVEFolha/1994/01/01/085.cg
src/test/resources/pt_BR/CHAVEFolha/1994/01/01/084.cg
src/test/resources/pt_BR/CHAVEFolha/1994/01/01/028.cg
src/test/resources/pt_BR/CHAVEFolha/1994/01/01/011.cg
src/test/resources/pt_BR/CHAVEFolha/1994/01/01/111.cg
src/test/resources/pt_BR/CHAVEFolha/1994/01/01/077.sgml
src/test/resources/pt_BR/CHAVEFolha/1994/01/01/010.sgml
src/test/resources/pt_BR/CHAVEFolha/1994/01/01/057.sgml
src/test/resources/pt_BR/CHAVEFolha/1994/01/01/060.cg
src/test/resources/pt_BR/CHAVEFolha/1994/01/01/103.sgml
src/test/resources/pt_BR/CHAVEFolha/1994/01/01/101.cg
src/test/resources/pt_BR/CHAVEFolha/1994/01/01/038.sgml
src/test/resources/pt_BR/CHAVEFolha/1994/01/01/129.cg
src/test/resources/pt_BR/CHAVEFolha/1994/01/01/070.cg
src/test/resources/pt_BR/CHAVEFolha/1994/01/01/059.sgml
src/test/resources/pt_BR/CHAVEFolha/1994/01/01/089.sgml
src/test/resources/pt_BR/CHAVEFolha/1994/01/01/083.sgml
src/test/resources/pt_BR/CHAVEFolha/1994/01/01/104.sgml
src/test/resources/pt_BR/CHAVEFolha/1994/01/01/007.sgml
src/test/resources/pt_BR/CHAVEFolha/1994/01/01/113.sgml
src/test/resources/pt_BR/CHAVEFolha/1994/01/01/107.cg
src/test/resources/pt_BR/CHAVEFolha/1994/01/01/105.cg
src/test/resources/pt_BR/CHAVEFolha/1994/01/01/076.cg
src/test/resources/pt_BR/CHAVEFolha/1994/01/01/013.sgml
src/test/resources/pt_BR/CHAVEFolha/1994/01/01/004.cg
src/test/resources/pt_BR/CHAVEFolha/1994/01/01/101.sgml
src/test/resources/pt_BR/CHAVEFolha/1994/01/01/028.sgml
src/test/resources/pt_BR/CHAVEFolha/1994/01/01/065.sgml
src/test/resources/pt_BR/CHAVEFolha/1994/01/01/035.sgml
src/test/resources/pt_BR/CHAVEFolha/1994/01/01/132.sgml
src/test/resources/pt_BR/CHAVEFolha/1994/01/01/130.sgml
src/test/resources/pt_BR/CHAVEFolha/1994/01/01/095.sgml
src/test/resources/pt_BR/CHAVEFolha/1994/01/01/033.cg
src/test/resources/pt_BR/CHAVEFolha/1994/01/01/081.sgml
src/test/resources/pt_BR/CHAVEFolha/1994/01/01/062.sgml
src/test/resources/pt_BR/CHAVEFolha/1994/01/01/072.cg
src/test/resources/pt_BR/CHAVEFolha/1994/01/01/008.sgml
src/test/resources/pt_BR/CHAVEFolha/1994/01/01/041.sgml
src/test/resources/pt_BR/CHAVEFolha/1994/01/01/063.sgml
src/test/resources/pt_BR/CHAVEFolha/1994/01/01/102.sgml
src/test/resources/pt_BR/CHAVEFolha/1994/01/01/050.sgml
src/test/resources/pt_BR/CHAVEFolha/1994/01/01/069.sgml
src/test/resources/pt_BR/CHAVEFolha/1994/01/01/020.sgml
src/test/resources/pt_BR/CHAVEFolha/1994/01/01/064.cg
src/test/resources/pt_BR/CHAVEFolha/1994/01/01/123.sgml
src/test/resources/pt_BR/CHAVEFolha/1994/01/01/062.cg
src/test/resources/pt_BR/CHAVEFolha/1994/01/01/116.sgml
src/test/resources/pt_BR/CHAVEFolha/1994/01/01/081.cg
src/test/resources/pt_BR/CHAVEFolha/1994/01/01/120.cg
src/test/resources/pt_BR/CHAVEFolha/1994/01/01/071.cg
src/test/resources/pt_BR/CHAVEFolha/1994/01/01/053.cg
src/test/resources/pt_BR/CHAVEFolha/1994/01/01/054.cg
src/test/resources/pt_BR/CHAVEFolha/1994/01/01/094.cg
src/test/resources/pt_BR/CHAVEFolha/1994/01/01/126.sgml
src/test/resources/pt_BR/CHAVEFolha/1994/01/01/082.sgml
src/test/resources/pt_BR/CHAVEFolha/1994/01/01/038.cg
src/test/resources/pt_BR/CHAVEFolha/1994/01/01/097.cg
src/test/resources/pt_BR/CHAVEFolha/1994/01/01/035.cg
src/test/resources/pt_BR/CHAVEFolha/1994/01/01/068.sgml
src/test/resources/pt_BR/CHAVEFolha/1994/01/01/102.cg
src/test/resources/pt_BR/CHAVEFolha/1994/01/01/118.cg
src/test/resources/pt_BR/CHAVEFolha/1994/01/01/019.sgml
src/test/resources/pt_BR/CHAVEFolha/1994/01/01/004.sgml
src/test/resources/pt_BR/CHAVEFolha/1994/01/01/030.sgml
src/test/resources/pt_BR/CHAVEFolha/1994/01/01/043.sgml
src/test/resources/pt_BR/CHAVEFolha/1994/01/01/117.sgml
src/test/resources/pt_BR/CHAVEFolha/1994/01/01/047.cg
src/test/resources/pt_BR/CHAVEFolha/1994/01/01/023.sgml
src/test/resources/pt_BR/CHAVEFolha/1994/01/01/053.sgml
src/test/resources/pt_BR/CHAVEFolha/1994/01/01/090.cg
src/test/resources/pt_BR/CHAVEFolha/1994/01/01/051.sgml
src/test/resources/pt_BR/CHAVEFolha/1994/01/01/046.cg
src/test/resources/pt_BR/CHAVEFolha/1994/01/01/120.sgml
src/test/resources/pt_BR/CHAVEFolha/1994/01/01/011.sgml
src/test/resources/pt_BR/CHAVEFolha/1994/01/01/022.sgml
src/test/resources/pt_BR/CHAVEFolha/1994/01/01/100.sgml
src/test/resources/pt_BR/CHAVEFolha/1994/01/01/127.cg
src/test/resources/pt_BR/CHAVEFolha/1994/01/01/029.sgml
src/test/resources/pt_BR/CHAVEFolha/1994/01/01/084.sgml
src/test/resources/pt_BR/CHAVEFolha/1994/01/01/027.cg
src/test/resources/pt_BR/CHAVEFolha/1994/01/01/096.cg
src/test/resources/pt_BR/CHAVEFolha/1994/01/01/061.sgml
src/test/resources/pt_BR/CHAVEFolha/1994/01/01/093.sgml
src/test/resources/pt_BR/CHAVEFolha/1994/01/01/125.sgml
src/test/resources/pt_BR/CHAVEFolha/1994/01/01/055.cg
src/test/resources/pt_BR/CHAVEFolha/1994/01/01/054.sgml
src/test/resources/pt_BR/CHAVEFolha/1994/01/01/088.cg
src/test/resources/pt_BR/CHAVEFolha/1994/01/01/077.cg
src/test/resources/pt_BR/CHAVEFolha/1994/01/01/065.cg
src/test/resources/pt_BR/CHAVEFolha/1994/01/01/048.sgml
src/test/resources/pt_BR/CHAVEFolha/1994/01/01/088.sgml
src/test/resources/pt_BR/CHAVEFolha/1994/01/01/019.cg
src/test/resources/pt_BR/CHAVEFolha/1994/01/01/128.sgml
src/test/resources/pt_BR/CHAVEFolha/1994/01/01/122.cg
src/test/resources/pt_BR/CHAVEFolha/1994/01/01/047.sgml
src/test/resources/pt_BR/CHAVEFolha/1994/01/01/109.cg
src/test/resources/pt_BR/CHAVEFolha/1994/01/01/046.sgml
src/test/resources/pt_BR/CHAVEFolha/1994/01/01/025.sgml
src/test/resources/pt_BR/CHAVEFolha/1994/01/01/117.cg
src/test/resources/pt_BR/CHAVEFolha/1994/01/01/052.cg
src/test/resources/pt_BR/CHAVEFolha/1994/01/01/112.sgml
src/test/resources/pt_BR/CHAVEFolha/1994/01/01/060.sgml
src/test/resources/pt_BR/CHAVEFolha/1994/01/01/010.cg
src/test/resources/pt_BR/CHAVEFolha/1994/01/01/063.cg
src/test/resources/pt_BR/CHAVEFolha/1994/01/01/131.cg
src/test/resources/pt_BR/CHAVEFolha/1994/01/01/114.sgml
src/test/resources/pt_BR/CHAVEFolha/1994/01/01/043.cg
src/test/resources/pt_BR/CHAVEFolha/1994/01/01/066.cg
src/test/resources/pt_BR/CHAVEFolha/1994/01/01/116.cg
src/test/resources/pt_BR/CHAVEFolha/1994/01/01/078.cg
src/test/resources/pt_BR/CHAVEFolha/1994/01/01/030.cg
src/test/resources/pt_BR/CHAVEFolha/1994/01/01/110.sgml
src/test/resources/pt_BR/CHAVEFolha/1994/01/01/014.cg
src/test/resources/pt_BR/CHAVEFolha/1994/01/01/031.cg
src/test/resources/pt_BR/CHAVEFolha/1994/01/01/074.cg
src/test/resources/pt_BR/CHAVEFolha/1994/01/01/016.sgml
src/test/resources/pt_BR/CHAVEFolha/1994/01/01/012.sgml
src/test/resources/pt_BR/CHAVEFolha/1994/01/01/089.cg
src/test/resources/pt_BR/CHAVEFolha/1994/01/01/026.cg
src/test/resources/pt_BR/CHAVEFolha/1994/01/01/005.sgml
src/test/resources/pt_BR/CHAVEFolha/1994/01/01/006.sgml
src/test/resources/pt_BR/CHAVEFolha/1994/01/01/009.cg
src/test/resources/pt_BR/CHAVEFolha/1994/01/01/114.cg
src/test/resources/pt_BR/CHAVEFolha/1994/01/01/017.cg
src/test/resources/pt_BR/CHAVEFolha/1994/01/01/131.sgml
src/test/resources/pt_BR/CHAVEFolha/1994/01/01/085.sgml
src/test/resources/pt_BR/CHAVEFolha/1994/01/01/128.cg
src/test/resources/pt_BR/CHAVEFolha/1994/01/01/080.sgml
src/test/resources/pt_BR/CHAVEFolha/1994/01/01/051.cg
src/test/resources/pt_BR/CHAVEFolha/1994/01/01/021.cg
src/test/resources/pt_BR/CHAVEFolha/1994/01/01/091.sgml
src/test/resources/pt_BR/CHAVEFolha/1994/01/01/044.sgml
src/test/resources/pt_BR/CHAVEFolha/1994/01/01/052.sgml
src/test/resources/pt_BR/CHAVEFolha/1994/07/13/038.sgml
EOF`

NOTICEMARKERSREGEXP="\(BEGIN\|END\) COPYRIGHT NOTICE"

HASHNOTICE="`mktemp -t noticeXXXXX`"
JAVANOTICE="`mktemp -t noticeXXXXX`"
PASCALNOTICE="`mktemp -t noticeXXXXX`"
XMLNOTICE="`mktemp -t noticeXXXXX`"
MDNOTICE="`mktemp -t noticeXXXXX`"

trap "rm -fR $HASHNOTICE $XMLNOTICE $JAVANOTICE $PASCALNOTICE $MDNOTICE" exit

(
	copyrightNotice | sed -e 's/^/# /'
) > "$HASHNOTICE"

(
	echo "<!--"
	copyrightNotice | sed -e 's/^/  /'
	echo "-->"
) > "$XMLNOTICE"

(
	echo ""
	echo "--------------------------------------------------------------------------------"
	copyrightNotice | sed -e 's/^/  /'
	echo ""
	echo "--------------------------------------------------------------------------------"
) > "$MDNOTICE"

(
	head -c 80 < /dev/zero | tr '\0' '*' | sed -e 's/^*/\//' -e 's/$/\n/'
	copyrightNotice | sed -e 's/^/ * /'
	head -c 80 < /dev/zero | tr '\0' '*' | sed -e 's/^*/ /' -e 's/*$/\/\n/'
) > "$JAVANOTICE"

(
	head -c 80 < /dev/zero | tr '\0' '*' | sed -e 's/^*/(/' -e 's/$/\n/'
	copyrightNotice | sed -e 's/^/ * /'
	head -c 80 < /dev/zero | tr '\0' '*' | sed -e 's/^*/ /' -e 's/*$/)\n/'
) > "$PASCALNOTICE"

findPreviousLicense() {
	FILE="$1"
	GREPOUTPUT=`grep "$NOTICEMARKERSREGEXP" -Zno "$FILE"` || return 1;
	echo "$GREPOUTPUT" | sed -e "s/:$NOTICEMARKERSREGEXP//g" | tr "\n" " "
}

stripPreviousLicense() {
	FILE="$1"
	INCRBEGIN="$2"
	INCREND="$3"

	LINES="`findPreviousLicense "$FILE"`" || return
	set -- $LINES; BEGIN="$1"; END="$2"

	BEGIN="$(($BEGIN $INCRBEGIN))"
	END="$(($END $INCREND))"

	sed -e "${BEGIN},${END}d" -i "$FILE"
}

stuffFirstLine() {
	FILE="$1"
	sed -i "$FILE" -f - << EOF
1i\
_
EOF
}

applyJava() {
	FILE="$1"
	stripPreviousLicense "$FILE" "-1" "+1"

	stuffFirstLine "$FILE"
	sed -i "$FILE" -e "1r $JAVANOTICE" -e "1d"
}

applyPascal() {
	FILE="$1"
	stripPreviousLicense "$FILE" "-1" "+1"

	stuffFirstLine "$FILE"
	sed -i "$FILE" -e "1r $PASCALNOTICE" -e "1d"
}

applyXML() {
	FILE="$1"
	stripPreviousLicense "$FILE" "-1" "+1"

	# aaa aaa dd dd:dd:dd aaa dddd
	if (head -n 1 "$FILE" | grep -q "<?") then
		sed -i "$FILE" -e "1r $XMLNOTICE"
	else
		stuffFirstLine "$FILE"
		sed -i "$FILE" -e "1r $XMLNOTICE" -e "1d"
	fi
}

applyMD() {
	FILE="$1"
	stripPreviousLicense "$FILE" "-2" "+2"

	cat "$MDNOTICE" >> "$FILE"
}

applyHash() {
	FILE="$1"
	stripPreviousLicense "$FILE"

	# aaa aaa dd dd:dd:dd aaa dddd
	if (head -n 1 "$FILE" | grep -q "^#... ... .. ..:..:.. ..S\?. ....$") then
		sed -i "$FILE" -e "1r $HASHNOTICE"
	elif (head -n 1 "$FILE" | grep -q "^#!") then
		sed -i "$FILE" -e "1r $HASHNOTICE"
	else
		stuffFirstLine "$FILE"
		sed -i "$FILE" -e "1r $HASHNOTICE" -e "1d"
	fi
}

EXCLUSIONS="`echo "$EXCLUSIONS" | sed -e "s|^|./|"`"

find \( -path './.git' -o -path '*/target' \) -prune -o -type f -print | grep -vF "$EXCLUSIONS" |
while read FILE
do
	
	case "`basename "$FILE"`" in
		*.java | *.aj | *.g4)
			applyJava "$FILE"
			;;
		*.pas)
			applyPascal "$FILE"
			;;
		*.xml | *.xsd)
			applyXML "$FILE"
			;;
		*.md)
			applyMD "$FILE"
			;;
		*)
			MAGIC="`file -b "$FILE"`"
			case "$MAGIC" in
				"XML  document text" | "XML document text")
					applyXML "$FILE"
					;;
				"ASCII text" | "ASCII text, with CRLF line terminators" | "POSIX shell script text executable")
					applyHash "$FILE"
					;;
				*)
					# don't know
					echo "$FILE: $MAGIC"
					;;
			esac
			;;
	esac
done

