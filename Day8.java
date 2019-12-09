/** Day 8 drivers and code
 */
class Day8
{
	public static void main(String[] args)
	{
		// Example1();

		Part1();
	}

	static void Part1()
	{
		System.out.println("Size of the image data is " + s_strPart1.length());

		Image image = new Image();
		image.Load(s_strPart1, 25, 6);

		System.out.println("Image has " + image.m_aLayer.length + " layers");

		Layer layerZero = null;
		int cZero = 0;

		for (Layer layer : image.m_aLayer)
		{
			int cZeroNew = 0;
			for (int pixel : layer.m_aPixel)
			{
				if (pixel == 0)
					++cZeroNew;
			}

			if (layerZero == null)
			{
				cZero = cZeroNew;
				layerZero = layer;
			}
			else if (cZeroNew < cZero)
			{
				System.out.println("Found a better layer!");

				cZero = cZeroNew;
				layerZero = layer;
			}
		}

		assert(layerZero != null);

		int cOne = 0;
		int cTwo = 0;

		for (int pixel : layerZero.m_aPixel)
		{
			switch (pixel)
			{
			case 1:
				++cOne;
				break;

			case 2:
				++cTwo;
				break;
			}
		}

		System.out.println("Found " + cZero + " zeroes in a layer; 1/2 are " + cOne + "," + cTwo + ": " + (cOne * cTwo));
	}

	static void Example1()
	{
		Image image = new Image();
		image.Load("123456789012", 3, 2);
	}

	static class Layer
	{
		int[] m_aPixel = null;
		Image m_image = null;

		// BB (davidm) if Layer was an inner class of Image, I wouldn't need to track it specifically here, which seems like
		//  more the java way of doing things

		public void Load(String strIn, Image image)
		{
			m_aPixel = new int[strIn.length()];
			m_image = image;

			for (int iCh = 0; iCh < strIn.length(); ++iCh)
			{
				m_aPixel[iCh] = Integer.valueOf(strIn.substring(iCh, iCh + 1));
			}
		}
	}

	static class Image
	{
		int m_xMax = 0;
		int m_yMax = 0;
		Layer[] m_aLayer = null;

		public void Load(String strIn, int xMax, int yMax)
		{
			m_xMax = xMax;
			m_yMax = yMax;
			int cPixelLayer = xMax * yMax;
			int cLayer = strIn.length() / cPixelLayer;
			m_aLayer = new Layer[cLayer];

			for (int iLayer = 0; iLayer < cLayer; ++iLayer)
			{
				m_aLayer[iLayer] = new Layer();
				m_aLayer[iLayer].Load(strIn.substring(iLayer * cPixelLayer, (iLayer + 1) * cPixelLayer), this);
			}
		}
	}

	static String s_strPart1 = "222222222222222222222021022222222202002222222202222222220222222222222221222222202211220222102122202220212222222221222222202122210222222022222222222212222222222222222222222120022222222222122222222222222222220222222222222221222222202211220222102222222220202222222221222222212222220222222222222222222202222222222222222222222221122222222212202222222222222222221220222221222220222222212222220222222022212222212202222221222222200122220222222122222222222212222222222222222222222021122222222202212222222212222222220220222220222222222222202200220022012122222220222202222220222222202222222222222122222222222220222222222222222222222120122222222222122222222212222222220221222220222222222222212211220222122022212222202202222220222222220222221222222122222222222220222222222222222222222122022222222222022222222202222222221222222221222212222222222201221022012222202222212222222220222222211222221222222122222222222222222222222222222222222220222222222212212222222202222222222220222220222222222222222200221122012222222222222202222220222222200022201222222122222222222221222222222222222222222222122222222202122222222202222222221222222222222210222122212212220222102121202222202222222220222222222022112222222222222222222211222222222222222222222221222222222212112222222212222222220221222220222220222022202201222222102220222221222212222221212222211222121222222222222222222202222222222222222222222021022222222222022222222212222222220222222220222210220022212200222122002220212222202202222220002222211122011222222022222222222200222222222222222222222221222222222222212222222212222222222220222220222211220022222221222022012222212222202202222220022222222222202222222122222222222221222222222222222222222020122222222212012022222202222222220221222221022200222122212202220022102120212222202202222221002222211122211222222222222222222210222222222222222222222021222222222212012112222222222022221222222221122201222122222220220122012022222220202222222222212222202122100222222122222222222202222222222222222222222021122222222222112022222212222122222221222221022212222022202211220222112122202221222202222220022222211222201222222122222222222211222222222222222222222221122222222202122022222222222122220221222222222202220122202222220022112021202221202202222221012222200222201221222022222222222201222222222222222222222121122222222212102222222112222222221221222222222220221222212220220122112122222220222202222222102222222222102222222022222222222212222222222222222222222121222222222212212112222022222021220221222220022221222122212202220122022022212222202212222221202222210222201222222222222222222201222222222222222222222222022222222222022012222112222120220220222221022211220022202221222222122020222220202202222222202222202122201220222222222222222210222222222222222222222121122222222202102212222202222021221222222220022221221222202212221022012021222221202202222220102222220022221222222022222222222202222222222222222222222022122222222212222122222002222020222222222220022202220022222201220122222222202220202222222221222222222222211220222222222222222222222222222222222222222020122222202202202002222002122022220221222222022201221222202202221022212122202220212222222222022222210122102202222022222222222222222222222222222222222221122222222222112022222212222022222221222220022212220122202220220022212221212221212222222220012222212022102221222022222222222221222222222222222222222020022222222222112222012002022021220220222222222220221122212201221222212220212021212212222221122222220122022211222022222222222222222222222222222222222021222222212212002112002122122122222222222222222201221122202200220122102220202222222222222220212222212022221222222122222212222211222222222222222222222221222222222222002112022122222120220221222222222211222022222201220222012220202122202212222221222222202022122201222022222212222210222222222222222222222022222222222212202222202212222021221221222220122202220122202201221222102220202020212202222220102222212122001201222222222222222222222202222222222222222220122222202202112202212002022121221222222221122221220022212200221222102021202220202202222221012222201222210210222122222212222202222222222222222222222220222222202212022122212222122120220220222220122222221222212212221122102020212221002202222222102222220122020212222122222222222222222222220222222222222121022222212222022102222202122120222221222220122200221122222220220022202121222020002212222222212222222022011221222222222212222200222212221222222222222121122222222202022212122022222021221220222221122212222022212220221122012021202020102202222222022222210122022210222122222212222221222202221222222222222021120222222222212122012212222022221222222220222202220122222211221122022221202221102212222221002222201022220221222122222222222210222212221222222222122021221222212222212022222122122122222220222222122222221222212200220122122120212220022202222221022222201122212210222222222202222221222202220222222222122021121222212212202212202122222221220222222222122211121022212220222022202020202221022222222222102222202022011202222222222202222222222222221222222222122020220222212222022122002112222120220221222221022201021222202201220022202121212120022212222222012222210222021200222022222222222210222202222222222222222221121212212202002002002022222220220220222221122221020122222210220222002020202220002202222220122222222220120210222222222222222220222222221222222222222021022222212212102212012222222021222201222221122201120122202202221022002221202221002202222221012222211121011210222022222222222202222202221222222222122022021222202222022012022202022020221200222221122221221222212212220022202122212220122202222220212222210220012221222022222222222210222212220222222222122220222222212212222212102102022022220210222222222210120022202221220222202220202222112222222221112222200221221202222122222212222202222212221222222222122021122202222202012102212022122221221200222222122221022222222222221222202122202220022202222221122222221220220222222122222212222221222222220222222222022122220202212212112222122112122222220211222222022222021122202221220122202121212121002112222022212222221121110200222122222222222211222202220222222222122221022212212202122112222222222220222202222222122202222022212202220122022020222021012012222221002222211022012222222122222222222202222222222222222222222102122212212222112212022112022122222200222220222211220222202211021122122221222222012022122121102222201122110212222022222222222201222222220222222222122100120002202222202102202120122221221202222220022220120022212202111220022120212222102112122022102222220022122211222222222222222212222202221222222222121110221202202202002102022110022222220222222020022221020022202211110021112222212022222002222022122222220021121220222022222222222201222202221222222222120102120202212222102112002220222021220200222122222202121022222212112120102221212122122102122220012222201120110212222222222202222220222222222222222222220220020202222212102202112022222020222211222121022210220022212201222221022121222021022102022020122222200120001202222222222212222212222222222222222222021010021012202212212212022110022022220221222020122210221222202202020121022121222122122222022222122222200020111212222022222202222222222212222222222222121000121222212212112212212222022121220212222220022202222222222201020022002221222221212202222220112222210221210220222022222222222220222202221222220222221212222022212212002202222210022120222210202220122221222122202222210221102122202222002212122122212222200022212212222022222212222211222202220222221222120201020122212222102212022121022020222222202020222201022122202210102221102120202220222212022222222222221120220211222022222212222210222212220222220222222020021202212222202112002122122020221211212220122222022122212210102022112221202222102212222222222222220222221221222122222202222222222212222222220222120120022222212212002212112122122220220221222122222221020222212222012220002221222222212222222021222222202220122202222222202212222211222212221222221222122221021022202222212212202021222222220221220222122211120122202211221020020122212220202202122022102222221221212212222122222202222200222212222222222222021122221002212222222212212202122221220221212120222201122022222211100020221222202221222022222121022222212201200200212222212222222202222212222222221222121000121022212222212002002022122121220201211120022222220122212212010021001021202121012222222122022222201102212211212122202212222212222212221222222222020111022102202212212212202200222221222222201021022220222120212201111020212120202021022112102121222222200102022212212222222212222220222202222222220222120010022122222202122022102112222122220202201020222202221222211210011120200121212222112222102221002222202212022221222022212212222211222202220222222222120122222122212202102022102111122220221220210220022211221122212200010222012021222021022202202021012222200022201210212022212222222200222202221222220222221220020022202222122102102212122222220222221022222221021022212211010221112221222022002022222020202222202102212200212022212222222221222222222222222222021221020122202202212102102200022120220202201122222201220020212210121221000022202122002012002121122222200110100221222122212202222222222222221222221222121022120222202202201022002120222122220211221221222221121022210221201020211120212022102012002121112222201212022220202222212202222221222222221222221222222111122222222212210002112220222221220202212222222212121120210221112222220221212020022202021120212222220010010211202222202222222201222212222222220222020220021102212202121122122021122122222221212020022221221020202210102020210220222020212222001222122222202112201202222022212202222221222202121222220222220100122120202202112112202100222022220200220021222222221020221220202121222222222022112112210220102222211220200220222222222212222212222222221222220222022101120112202222002202012001222122222202212122222212021221202201211121100120202022212222111122202222212200012211202122212222222201222212120222220222121222021000212222201002222221121221221222200121022221122222201221220121101021222020202022200021212222200011211210202222202222222221222212220222220222021021221002112222122012012210221120222211211122022210221122212202112120110222202020202102220022222222200112010221202112202202222212222222121222220222222111222002002202002202222122220221221020221021222212222020220210011221122221202120212002100120202222212120202212212122202202222221222202220222220222222100121120002222102200102200021221220111221221121210221020212202010222100022202222222122022121000222221102001201212022202202222222222212122222220222121020021222002202122202022110222022220101200222221200020020201201121021102120202020112212111121120222222111111212222122212222022211222202121202202222122002022211222212000102202002120021221212200221222222221121212222010121110020212021112212221120222222211101112210202012222202122212222202122012202222021210021212022202201012002220120220222210200020120212220120210200002220102222222122022122010220212222211122220210222022222212222222222222120002202222221220020010202212220201002100220021220000221221120221022122202210101021212221222020112012201120020222222110220212202222212202022222222222221212202222220102020010002222222120102001220220220100201022122210120221212202200120022120212220102012211020111222222200021200212002222202222201222202221202210222120122122121112212020002102121220121221002200020222220122122212201211220212220212121202012012220210222202122201222202212202202120201222222122012220222020122020212212212120201022012222222211112202001022222021021200221212221001122212120222012210122022222212200222222212122222222121221222202120112201222020111020101112202021001202010020022200002221100121201022220201211211222102121212220222212021120000222100211202212212012202222121221222222021212201222021100220021002222021000102211122022201210221110220201222022200220120120100021212121002012210122110220222122222220202112212222022212222212220022200222221020120211202202010020202102221120201112212221022201120020210202200122112222222122212102002122222221001221021200202212222212222200222222121212210222222120021220022212211101022022221121221010212002121212222121210212101122010002212022112122020120120221210200001201222102202212220001222212222102202222021011120012112222121210212110022021200221222200121201121020211221020022012212202222012112201022102222222101012200202102222212222101222212121022201222220001021120102212112000002102021020220200201021220200121020210202201221111120212120122122201120201222121110100202212202212222120112222222220202221222220110222011002202221021222111222222210202201010221202020222200212012022021200222022102102011222000222222021022202222022222202120002222202120202201202020000120212022212110020222222022121211212200000022210021120210221102222121121202122012022120121210222221112001201212102002212022101222212222012220202121102221220022212101221112212022021200201121000120221222022201210111022101121212220122122011121000220220012220221212012112222222102222212122102211222022011221012012222021011022020020020202200020210020221222121211201002020110022222221022002221021222221211011002210212222002222221121122202020212200222020222022010022222220110002102020020210020211201022202122120212211010120112122212221222012212122002222021220102202212212022202022001022202220212211202020012022112122202012221202221022122201102122022220212220222221212220220210020202022012102000120122222100200000211110112222202021012022222120202222212220001222020022202210010012002221220211020102020200201121121220011121022101022222020212122020120210221101212211200221002212212120102222212221112212222022012121211002202012000202220022022202101212100122201020120222002221221201211212222222112201222202220212010020212222102102202020221222222020002221202222020121201202212110000012202021220201220000102220222022022212212210121121021202122012202101121110222220120201222011022122222121102122112022102212212220201120020202202101212012110121022221002121001220202020022222222222022000020202120212112110020121220120211100211221222202202122220222102220102220212020021210001012222112210012022122222202110121112021210021122211211010220022011202120222122222122111220221200100212122002222212121201022212021212221202122211210211002212210001002222221020222200020020210221021122220200111020001120212220122022202021221222101101022200020222202202022100122222121202211212020200202111102212220222022210122121200201212000100212021121220110120220102111202020102222222122220222011102120222111102220102122201222022021012201222021200220212002212110010212200122021212022220020110222121022212020010221021021212022202122022120220221121020012221211222110022020112002102022202202222021122102011002212002020022112222222220111012101012210120120212220000021022200202222102022212222200211001110020201220212211010121110012000220202202212122220112010202202022000112221020121222000102220201210121122201122001021011121222222102222021220110212220121112200100112202022020122112000021112221112220210022100022212210222022001021122202022012110002220221220222020212222111021222220212200201121212220100210212201200212201222120012100212110111102010100022220012200121100101020112102200021110020021101012112202100222112101021000011111220022100100221021210022212011010011000221211102";
}
