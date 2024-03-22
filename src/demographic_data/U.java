package demographic_data;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.io.Writer;
import java.net.HttpURLConnection;
import java.net.InetSocketAddress;
import java.net.MalformedURLException;
import java.net.Proxy;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.openqa.selenium.WebDriver;




//import org.openqa.selenium.firefox.FirefoxDriver;
//import org.openqa.selenium.firefox.FirefoxOptions;
//import org.openqa.selenium.firefox.FirefoxDriver
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.firefox.FirefoxProfile;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import javax.security.cert.X509Certificate;

//import org.openqa.selenium.firefox.FirefoxProfile;




import org.apache.bcel.generic.GOTO;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.SystemUtils;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.openqa.selenium.By;
import org.openqa.selenium.Cookie;
import org.openqa.selenium.JavascriptExecutor;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxBinary;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.phantomjs.PhantomJSDriver;
import org.openqa.selenium.phantomjs.PhantomJSDriverService;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.supercsv.io.CsvListReader;
import org.supercsv.prefs.CsvPreference;
import com.shatam.scrapper.ShatamChrome;
import com.shatam.scrapper.ShatamFirefox;

public class U {

	public final static void stripAll(String[] vals) {
		Arrays.stream(vals).map(String::trim).toArray(unused -> vals);
	}

	/**
	 * @author priti
	 */
	public static final String MY_PHANTOMJS_PATH = System.getProperty("user.home") + File.separator + "phantomjs";
	public static final String MY_CHROME_PATH = System.getProperty("user.home") + File.separator + "chromedriver";

	public static void setUpChromePath() {
		if (SystemUtils.IS_OS_LINUX) {
			System.setProperty("webdriver.chrome.driver", MY_CHROME_PATH);
		}
		if (SystemUtils.IS_OS_WINDOWS) {
			System.setProperty("webdriver.chrome.driver", MY_CHROME_PATH + ".exe");
		}
	}

	public static final String MY_GECKO_PATH = System.getProperty("user.home") + File.separator + "geckodriver";

	public static void setUpGeckoPath() {
		if (SystemUtils.IS_OS_LINUX) {
			System.setProperty("webdriver.gecko.driver", MY_GECKO_PATH);
		}
		if (SystemUtils.IS_OS_WINDOWS) {
			System.setProperty("webdriver.gecko.driver", MY_GECKO_PATH + ".exe");
		}
		clearFirefoxConsoleLogs();
	}

	public static WebDriver headLessDriver = null;

	// Clear selenium log from console
	public static void clearFirefoxConsoleLogs() {
		
		
	//	System.setProperty(FirefoxDriver.DRIVER_USE_MARIONETTE, "true");
	//	System.setProperty(FirefoxDriver.SystemProperty.BROWSER_LOGFILE,
	//			System.getProperty("user.home") + File.separator + "Selenium_logs.txt");
		
		
		
		
		
		U.log("[::: Clear Firefox Console Logs ::::]");
	}

	public static FirefoxOptions getFirefoxOptions() {
		FirefoxOptions options = new FirefoxOptions();
		options.addArguments("--headless");
		// Disable geolocation
		options.addArguments("--disable-geolocation");
		options.addPreference("permissions.default.image", 2);
		// flash player
		options.addPreference("plugin.state.flash", 2);
		return options;
	}

	public static FirefoxProfile getFirefoxProfile() {
		FirefoxProfile profile = new FirefoxProfile();
		profile.setPreference("permissions.default.image", 2);
		return profile;
	}

	public static FirefoxBinary getFirefoxBinary() {
		File pathToBinary = new File(System.getProperty("user.home") + File.separator + "firefox/firefox");
		FirefoxBinary ffBinary = new FirefoxBinary(pathToBinary);
		return ffBinary;
	}

	public static DesiredCapabilities getFirefoxCapabilities() {
		DesiredCapabilities capabilities = DesiredCapabilities.firefox();
		// capabilities.setJavascriptEnabled(true);
		capabilities.setCapability(FirefoxOptions.FIREFOX_OPTIONS, getFirefoxOptions());

		return capabilities;
	}

	public static String getHtmlHeadlessFirefox(String url, WebDriver driver) throws IOException, InterruptedException {

		String html = null;
		String Dname = null;

		String host = new URL(url).getHost();
		host = host.replace("www.", "");
		int dot = host.indexOf("/");
		Dname = (dot != -1) ? host.substring(0, dot) : host;
		File folder = null;

		folder = new File(U.getCachePath() + Dname);
		if (!folder.exists())

			folder.mkdirs();
		String fileName = U.getCacheFileName(url);

		fileName = U.getCachePath() + Dname + "/" + fileName;

		File f = new File(fileName);
		if (f.exists()) {
			return html = FileUtil.readAllText(fileName);
			// U.log("Reading done");
		}
		// int respCode = CheckUrlForHTML(url);
		Thread.sleep(8000);
		// if(respCode==200)
		{

			if (!f.exists()) {
				BufferedWriter writer = new BufferedWriter(new FileWriter(f));
				driver.get(url);
				((JavascriptExecutor) driver).executeScript("window.scrollBy(0,400)", "");
				html = driver.getPageSource();
				// U.log("Current Url : "+ driver.getCurrentUrl());
				Thread.sleep(2000);
				writer.append(html);
				writer.close();

			} else {
				if (f.exists()) {
					html = FileUtil.readAllText(fileName);
					U.log("Reading done");
				}
			}
			return html;
		}

	}// //

	/**
	 * @author priti
	 */
	// Format million price
	public static String formatMillionPrices(String html) {
		Matcher millionPrice = Pattern.compile("\\$\\d\\.\\d M", Pattern.CASE_INSENSITIVE).matcher(html);
		while (millionPrice.find()) {
			// U.log(mat.group());
			String floorMatch = millionPrice.group().replace(" M", "00,000").replace(".", ","); // $1.3 M
			html = html.replace(millionPrice.group(), floorMatch);
		} // end millionPrice
		return html;
	}

	public static String getHardCodedPath() {
		String Regex = "Harcoded Builders";
		String Filename = System.getProperty("user.home");
		if (Filename.contains("/")) {
			Regex = "/Cache/Harcoded Builders/";
		} else {
			Regex = "\\Cache\\Harcoded Builders\\";
		}
		Filename = Filename.concat(Regex);
		if (!Filename.equals(Regex)) {
			Regex = Regex.toLowerCase();
		}
		return Filename;
	}

	public static String getNoHtml(String html) {
		String noHtml = null;
		noHtml = html.toString().replaceAll("\\<.*?>", " ");
		return noHtml;
	}

	public static String getCityFromZip(String zip) throws IOException {
		// http://maps.googleapis.com/maps/api/geocode/json?address=77379&sensor=true
		String html = U.getHTML("http://ziptasticapi.com/" + zip);
		String city = U.getSectionValue(html, "city\":\"", "\"");
		return city.toLowerCase();
	}

	public static String[] getGoogleLatLngWithKey(String add[]) throws IOException {
		String addr = add[0] + "," + add[1] + "," + add[2];
		addr = "https://maps.googleapis.com/maps/api/geocode/json?address=" + URLEncoder.encode(addr, "UTF-8")
				+ "&key=AIzaSyAeG9lLU8fWh8rWcPivHDwxLAM4ZCInpmk";
		U.log(addr);
		U.log(U.getCache(addr));
		String html = U.getHTML(addr);

		String sec = U.getSectionValue(html, "location", "status");

		String lat = U.getSectionValue(sec, "\"lat\" :", ",");
		if (lat != null)
			lat = lat.trim();
		String lng = U.getSectionValue(sec, "\"lng\" :", "}");
		if (lng != null)
			lng = lng.trim();
		String latlng[] = { "", "" };
		String status = U.getSectionValue(html, "status\" : \"", "\"");
		if (status.trim().equals("OK")) {
			latlng[0] = lat;
			latlng[1] = lng;
			return latlng;
		} else
			return latlng;
	}

	public static String[] getGoogleAddressWithKey(String latLng[]) throws Exception {

		String st = latLng[0].trim() + "," + latLng[1].trim();
		String addr = "https://maps.googleapis.com/maps/api/geocode/json?latlng=" + st;
		String key = "AIzaSyAeG9lLU8fWh8rWcPivHDwxLAM4ZCInpmk";

		U.log("With key" + U.getCache(addr));
		String html = U.getHTMLForGoogleApiWithKey(addr, key);
		String status = U.getSectionValue(html, "status\" : \"", "\"");

		if (status.trim().equals("OK")) {
			String txt = U.getSectionValue(html, "formatted_address\" : \"", "\"");
			U.log("Address txt Using gmap key :: " + txt);
			if (txt != null)
				txt = txt.trim();
			txt = txt.replace("7970 U.S, Co Rd 11", "7970 US Co Rd 11")
					.replace("Brooklyn Trails, 22202 Porter Mountain Trl", "22202 Porter Mountain Trl")
					.replaceAll("The Arden, |TPC Sugarloaf Country Club, ", "").replace("50 Biscayne, 50", "50");
			txt = txt.replaceAll("110 Neuse Harbour Boulevard, ", "");
			txt = txt.replaceAll(
					"Waterview Tower, |Liberty Towers, |THE DYLAN, |Cornerstone, |Roosevelt Towers Apartments, |Zenith, |The George Washington University,|Annapolis Towne Centre, |Waugh Chapel Towne Centre,|Brookstone, |Rockville Town Square Plaza, |University of Baltimore,|The Galleria at White Plains,|Reston Town Center,",
					"");
			String[] add = txt.split(",");
			add[3] = Util.match(add[2], "\\d+");
			add[2] = add[2].replaceAll("\\d+", "").trim();
			return add;
		} else {
			return new String[] { "", "", "", "" };
		}
	}

	public static String getHTMLForGoogleApiWithKey(String path, String key) throws Exception {
		path = path.replaceAll(" ", "%20");
		String fileName = U.getCache(path);
		File cacheFile = new File(fileName);
		if (cacheFile.exists()) {
			if (cacheFile.length() > 400) {
				return FileUtil.readAllText(fileName);
			} else if (cacheFile.length() < 400) {
				cacheFile.delete();
			}
		}
		URL url = new URL(path + "&key=" + key);
		String html = null;
		// Proxy proxy = new Proxy(Proxy.Type.HTTP, new InetSocketAddress("45.77.146.9",
		// 55555));
		final URLConnection urlConnection = url.openConnection(); // proxy
		// Mimic browser
		try {
			urlConnection.addRequestProperty("User-Agent",
					"Mozilla/5.0 (Windows NT 6.1; WOW64; rv:10.0.2) Gecko/20100101 Firefox/10.0.2");
			urlConnection.addRequestProperty("Accept", "text/css,*/*;q=0.1");
			urlConnection.addRequestProperty("Accept-Language", "en-us,en;q=0.5");
			urlConnection.addRequestProperty("Cache-Control", "max-age=0");
			urlConnection.addRequestProperty("Connection", "keep-alive");
			final InputStream inputStream = urlConnection.getInputStream();
			html = IOUtils.toString(inputStream);
			inputStream.close();
			if (!cacheFile.exists())
				FileUtil.writeAllText(fileName, html);
			return html;
		} catch (Exception e) {
			U.log(e);

		}
		return html;
	}

	public static String[] getAddressGoogleApi(String latlng[]) throws Exception {
		String st = latlng[0].trim() + "," + latlng[1].trim();
		String addr = "https://maps.googleapis.com/maps/api/geocode/json?latlng=" + st;
		U.log(addr);

		U.log(U.getCache(addr));
		String html = U.getPageSource(addr);
		String status = U.getSectionValue(html, "status\" : \"", "\"");
		U.log("GMAP Address Status Without Key : " + status);
		if (!status.contains("OVER_QUERY_LIMIT") && !status.contains("REQUEST_DENIED")) {
			String txt = U.getSectionValue(html, "formatted_address\" : \"", "\"");
			U.log("txt:: " + txt);

			if (txt.contains("+")) {
				txt = U.getSectionValue(html.replace("\"formatted_address\" : \"" + txt + "\",", ""),
						"formatted_address\" : \"", "\"");
			}
			if (txt != null)
				txt = txt.trim();
			txt = txt.replace("Hummingbird Trail, 2954-2980 Kuehner Dr", "2954-2980 Kuehner Dr")
					.replace("7970 U.S, Co Rd 11", "7970 US Co Rd 11")
					.replace("7100 E, 7100 East Green Lake Dr N", "7100 East Green Lake Dr N")
					.replace("Brooklyn Trails, 22202 Porter Mountain Trl", "22202 Porter Mountain Trl")
					.replace("Foothill Dr &, E Vista Way", "Foothill Dr & E Vista Way")
					.replace("16063 S, Selfridge Cir", "16063 S Selfridge Cir")
					.replace("13th Ave &, Grangeville Blvd", "13th Ave & Grangeville Blvd")
					.replace("N Clovis Ave &, E Shepherd Ave", "N Clovis Ave & E Shepherd Ave")
					.replace("Paseo Del Sur &, Templeton St", "Paseo Del Sur & Templeton St")
					.replace("N Dinuba Blvd &, Riverway Dr", "N Dinuba Blvd & Riverway Dr")
					.replace("Williams Airport, 20266 Airfield Ln", "20266 Airfield Ln")
					.replace("Blue Crane Cir, South Carolina 29588", "Blue Crane Cir, Myrtle Beach, SC 29588")
					.replace("Rehbein and, County Hwy 21", "Rehbein and County Hwy 21")
					.replace("Summers Cor, 104 Rushes Row", "Summers Cor 104 Rushes Row")
					.replace("5637 Lowell, Ayers Cliff St", "5637 Lowell Ayers Cliff St")
					.replace("N Clovis Ave &, E Shepherd Ave", "N Clovis Ave & E Shepherd Ave").replace("Indiana", "IN")
					.replace("Midtown Ave / MUSC Health East Cooper, South Carolina 29464",
							"Midtown Ave , Mt Pleasant, SC 29464")
					.replace("Shaw Ave and, N Grantland Ave", "Shaw Ave and N Grantland Ave")
					.replace("770D-A, Lighthouse Dr", "770D-A Lighthouse Dr")
					.replaceAll("The Arden, |TPC Sugarloaf Country Club, ", "");
			txt = txt.replaceAll("110 Neuse Harbour Boulevard, ", "").replace("M239+9H Socastee, SC, USA",
					"Myrtle Beach, Socastee, SC 29588, USA");
			txt = txt.replaceAll(
					"Waterview Tower, |Liberty Towers, |THE DYLAN, |Cornerstone, |Roosevelt Towers Apartments, |Zenith, |The George Washington University,|Annapolis Towne Centre, |Waugh Chapel Towne Centre,|Brookstone, |Rockville Town Square Plaza, |University of Baltimore,|The Galleria at White Plains,|Reston Town Center,",
					"");
			String[] add = txt.split(",");

			add[3] = Util.match(add[2], "\\d+");
			add[2] = add[2].replaceAll("\\d+", "").trim();

			// U.log("zip:::::::"+add[3]);
			return add;
		} else if (getGoogleAddressWithKey(latlng) != null) {
			return getGoogleAddressWithKey(latlng);
		} else {
			return getAddressHereApi(latlng);
		}
	}

	public static String[] getAddressGoogleApiProxy(String latlng[]) throws IOException {
		String st = latlng[0].trim() + "," + latlng[1].trim();
		String proxy = "http://75.119.204.81:3302/gproxy?to=";

		String addr = "https://maps.googleapis.com/maps/api/geocode/json?latlng=" + st;
		proxy = proxy + addr;
		U.log(proxy);

		U.log(U.getCache(proxy));
		String html = U.getPageSource(proxy);

		String txt = U.getSectionValue(html, "formatted_address\" : \"", "\"");
		U.log("txt:: " + txt);
		if (txt != null)
			txt = txt.trim();
		txt = txt.replaceAll("The Arden, |TPC Sugarloaf Country Club, ", "");
		txt = txt.replaceAll("110 Neuse Harbour Boulevard, ", "");
		txt = txt.replaceAll(
				"Waterview Tower, |Liberty Towers, |THE DYLAN, |Cornerstone, |Roosevelt Towers Apartments, |Zenith, |The George Washington University,|Annapolis Towne Centre, |Waugh Chapel Towne Centre,|Brookstone, |Rockville Town Square Plaza, |University of Baltimore,|The Galleria at White Plains,|Reston Town Center,",
				"");
		String[] add = txt.split(",");
		add[3] = Util.match(add[2], "\\d+");
		add[2] = add[2].replaceAll("\\d+", "").trim();

		// U.log(lat);
		return add;
	}

	public static String[] getlatlongGoogleApi(String add[]) throws IOException {
		String addr = add[0] + "," + add[1] + "," + add[2];
		addr = "https://maps.googleapis.com/maps/api/geocode/json?address=" + URLEncoder.encode(addr, "UTF-8");
		U.log(addr);
		U.log(U.getCache(addr));
		String html = U.getHTML(addr);
		String status = U.getSectionValue(html, "status\" : \"", "\"");
		U.log("GMAP Address Status Without Key : " + status);
		if (!status.contains("OVER_QUERY_LIMIT") && !status.contains("REQUEST_DENIED")) {
			String sec = U.getSectionValue(html, "location", "status");
			String lat = U.getSectionValue(sec, "\"lat\" :", ",");
			if (lat != null)
				lat = lat.trim();
			String lng = U.getSectionValue(sec, "\"lng\" :", "}");
			if (lng != null)
				lng = lng.trim();
			String latlng[] = { lat, lng };
			// U.log(lat);
			return latlng;
		} else if (getGoogleLatLngWithKey(add) != null) {
			return getGoogleLatLngWithKey(add);
		} else {
			return getlatlongHereApi(add);
		}
	}

	public static String[] getlatlongGoogleApiProxy(String add[]) throws IOException {
		String proxy = "http://75.119.204.81:3301/gproxy?to=";
		String addr = add[0] + "," + add[1] + "," + add[2];
		addr = "https://maps.googleapis.com/maps/api/geocode/json?address="// 1138
																			// Waterlyn
																			// Drive","Clayton","NC
				+ URLEncoder.encode(addr, "UTF-8");
		proxy = proxy + addr;
		U.log(proxy);
		U.log(U.getCache(proxy));
		String html = U.getHTML(proxy);
		String sec = U.getSectionValue(html, "location", "status");
		String lat = U.getSectionValue(sec, "\"lat\" :", ",");
		if (lat != null)
			lat = lat.trim();
		String lng = U.getSectionValue(sec, "\"lng\" :", "}");
		if (lng != null)
			lng = lng.trim();
		String latlng[] = { lat, lng };
		// U.log(lat);
		return latlng;
	}

	private static ShatamFirefox driver;
	private static HashMap<String, String> communityType = new HashMap<String, String>() {
		{
			put("is a Swim Community|Swim Community", "Swim Community");
			put("Lakeview Lots Available|lakeside living|lakefront adventures|lake-style living|living at Lakeside|Lakeside Estates|luxurious lakeside community|lakeside living|Lakeside Master Planned Community|a lakeside community|accessible lakeside lifestyle|beautiful lakeside community|Lake community|[l|L]akeside [c|C]ommunity,|, Lakeside Community| Lakeside section of|lakeside community|features lakeside fun",
					"Lakeside Community");
			put("A New 62+|62 years of age or older|aged 62 and older|62\\+ age-restricted|62\\+ Senior Apartment Community|62 years of age or better|62 years and older|aged 62 and better|62\\+ apartment community",
					"62+ Community");
			put("Active Adult Living|active adult|active-adult |active adult|active adult|Active Adult</li>",
					"Active Adult");
			put("Golf Lake Estates| golf, |golf and|Golf Center</li>|<li>Nearby Golf,|golf resorts|round of golf | Golf Country Club|golf, and|include golf |golfer’s paradise| championship golf |Golf Trail|Needwood Golf Course|Golf course and|<span>Golf</span>|TopGolf|Golf Course</td>|golf memberships|golfing|golfing area|<li>Golf</li>|private golf holes|Top Golf</span>|Golf\\s+</li>| Golf Club|Golf Club</a>|Golf &amp; Country Club|Golf & Country Club| Golf Club,|Golf Club |golf course|golf courses|golf resort community|golf communities|gated golf & tennis community|Golf Course|Golf Course</h4>|golf courses|Golf and Country Club|Golf Community|golf &amp; country clubs",
					"Golf Course");
			put("55+ senior community|55 + community|55+ Community|adults 55 and over|adults aged 55+|his fun-filled 55|seniors 55 and over|55\\+ Condos|55\\+ age restricted community|residents age 55 and up|bring the 55\\+ Live Better|buyers 55 & over|55\\+ Apartments|Best 55\\+ Lifestyle|community for ages 55\\+|ages 55\\+ |55\\+ Life|55\\+ Community for|over the age of 55| Amazing 55\\+ Lifestyle| 55\\+ townhome living|55\\+ Age-Qualified|55\\+ Epcon Community|55-plus community|residents ages 55\\+|55\\+ Luxury Retirement |55\\+ Living|55\\+ lifestyle community|active adult 55\\+|55\\+ Retirement Living|55\\+ resort community|55\\+ single-family|55-and-better couples|55+ living|ages 55 and up|community for those 55\\+|55 and older|active adults 55\\+|55\\+ age-restricted|55\\+ resort-class community|55\\+ gated community|\\+55 age group|Best 55\\+ Communities|55-and-better living|Active Adults aged 55 or better|55 years or better in residence|55 plus residents |55-years of age and better|55-Plus communities|55 and over gated|55 and over community|active 55-and-better lifestyle| 55-and-better community|55 and Bette|55\\+ Residents|55 and older community|55+ neighborhood|55+ community|Community 55\\+|55\\+ Active Adult|\\(55\\+\\) community|active adult \\(55\\+\\)|55\\+ age-qualified community|\\(55\\+\\) gated community| lifestyle for 55\\+ |55\\+ neighborhood|55\\+ residents| 55\\+ Community|55\\+ Active|community \\(55 years \\+\\) |active adults age 55",
					"55+ Community");
//			put("\\d{2}\\+ luxury community", "55 Plus Community");
			put("Waterfront at The Vineyards on Lake Wylie|Waterfront at McLean|Waterfront Homesites|both waterfront|water-front homesites|Waterfront Community|waterfront amenities | waterfront homes|magnificent water features|serene waterfront community|waterfront neighborhood |exclusive waterfront living|beautiful waterfront views|waterfront lifestyle|Waterfront Resort-style Living|waterfront community|waterfront lots",
					"Waterfront Community");
			put("Lake offering|Lakefront|lakefront homesites|lakefront resort style|lake front townhomes|lakefront and parkside living|Lakefront Series|lake-front Community|lakefront community|lake front community",
					"Lakefront Community");

			put("A Master Plan Community|master-planned amenities|Master Plan Community</li>|Master-Planned Community|masterfully planned|master-planned community|Master-Planned Community| best master-plans of |master-plan community|Masterplanned Community|masterplanned community|Master-plan living|planned master community|Master Planned|masterfully-planned community|masterfully planned community| masterplan|Master-Planned|master-planned|master planned|master- planned|Master Plan|Master Plan|master-planned|Master Planned Community|masterplanned community",
					"Master Planned");
			put("Green Community", "Green Community");
			put("resort-like|Resort-Style|Resort-Worthy|resort-style|resort-style|resort-inspired|resort-inspired community|enowned resort|resort-class lake living| resort-like living|resorts pool|resort-style infinity pool|luxurious resorts|resort-inspired recreational |resort-inspired clubhouse |resort-inspired living|resort-style swimming pools|Resort- style pool|Amenities/Resort|resort experience|resort-like activities|resort-style living|Resort-like Pool|resort-inspired amenities|resort-style pool|Resort Style Pool|Resort Lifestyle|resort inspired community|resort like style amenities|resort-type lifestyle|resort-like amenities|resort-like environment| luxurious resort-style communities|Casino Resort| luxury resort|golf courses and resort |Golf and Resort|resort class amenities|Resort-style community|Golf Resort| The Resort Series|resort-inspired lifestyle|The Resort at the Groves|resort-like lifestyle|resort style amenities|resort-style amenities|resort pleasures|Resort Homes|Resort Pool|Resort golf course|resort community|Resort-style|Resort-Style|Resort and Golf Course|resort living|resort-style|posh resort|Resort style|resort features|Resort-style|resort style living| resort pool|resort-style|Smokey Resort|resort inspired amenity|Resort-style|Resort-style|resort amenities|resort-style amenities|Waterfront Resort-style Living|Resort-Style Living",
					"Resort Style");
			put("gated community|gated-community|Manned, Gated Security| <li>Gated,|Gated</div>|Gated, Natural Gas Community|Access Gate|Gated, Pool|Gated luxury|Private gated community|Gated Community|Controlled Access/Gated|Gated\\s*</p>|<br>Gated<br>|<li>Gated</li>|Gate House|gated community|gated single family|Gated Security|gated |Gated |Gated Community</li>| gated, ",
					"Gated Community");
			put("Country Club-Style|Country Club Community|Country Club", "Country Club");
			put("riverfront|Riverfront Homesites|riverfront property|Riverfront Community|riverfront community|riverfront land|Riverfront offers",
					"Riverfront Community");

		}
	};

	private static final String propStatusIgnore = "coming spring 2022|Model Home Now Open|Model Home now available|Model Homes Now Available|Models coming this summer|Pricing Now Available|Appointments now available|Final Balance Home Now Available|floorplan[s]* is now available|Floorplans–Now Available|Amenity Site Coming Soon|[P|p]ricing\\s*[is]*\\s*[N|n]ow [A|a]vailable|Community Garden Coming Soon|Clubhouse coming soon|Coming Soon: On-site Community|Price Coming Soon|Now Open - Two new model|Pickleball Courts coming soon|Amenity expansion coming soon|Coming Soon - Amenity|Coming Soon - Clubhouse|New Flats Coming Soon|design[s]* now available|Model[s]* coming Summer 2018|plan[s]*[ are ]*now available|floor plans now available|Model Home is Now Available|Site Plan Now Available|model home coming soon|Sales Center Opening Spring 201[8|9]|Model Homes Open Summer 2018|Model Home, now OPEN|now open for sales|Model Coming Winter 2017|tot lot coming soon|New Model\\s*Coming Soon|pre-selling out |Model home is now selling|model opening September 2017|model home now available|now open for sale|model is now available|model home is COMING SOON|model home is coming soon|New Floor Plans Just Released|now open for leasing|Now Open For Presales|Models Now Selling|New floorplan now available|Grand Opening Pricing|ONE HOME REMAINING \\(MODEL\\)|model residences now selling|New floor plans were just released|NOW OPEN - NEW MODEL|Floor Plan Coming Soon|#\">Coming Soon</a></li>|Model Home now available for purchase|Pre-Grand Opening|Preselling Now|Preselling Now|New Model - Now Selling|NEW MODELS COMING EARLY 2017|Pre-Selling Now|Model Home coming |MODELS NOW OPEN|More Info Coming Soon|Model[s]* Now Available|Models now available!|New Model Complex Now Open|Final Phase Now Preselling|join us for our Grand Opening|Center - now open|MODEL NOW OPEN|Grand Opening Prices|Model NOW Open|Model Coming Soon|Plan sold out|model homes are now open|Model Homes Now Selling|Model Coming Late 2015|Model Opening Soon|New Mode\\s*Coming Soon|school currently under construction|Grand Opening Icon|Model Home Opening Soon|Now Open Image|<!-- Quick Move-Ins -->|MODEL NOW OPEN|AREA INFORMATION COMING SOON|Price is coming soon|Sales Center Now Open|Model Home COMING SOON|Model Home Coming Soon|walking trails coming soon|Close-Out Special|Coming Soon</a> |Park Coming Soon|Models coming soon|Home Coming Soon|Model Coming Soon|Office Now Open|plans coming soon|Home Under Construction|Model Homes Now Open|Homes Opening Soon|homes are under construction|are currently under construction|pool coming soon|home is now open|model is now open|Model Grand Opening|Home Grand Opening|Opening December 2015|Model now under construction|Clubhouse Now Open|homes under construction|Clubhouse NOW OPEN|MODEL HOME NOW OPEN|Price Is Coming Soon|Area Now Open|Lot - Now Open|Amenities coming|Park is Now Open";
	private static ArrayList<String> propStatus = new ArrayList<String>() {
		{
//			add("New Section Now Open");
			add("Quick Move-in Homes");
			add("Coming January 2023");
			add("COMING Winter 2023");
			add("New section lake front lots open now");
			add("Arriving Early 2023");
//			add("NEW PHASE NOW SELLING");
			add("OPENING SOON");
			add("PHASE 2 AVAILABLE");
			add("grand opening soon");
			add("Waterfront Lots Now Available");
			add("New Section Coming Fall 2022");
			add("Now Selling in Final Section");
			add("New Homes Available Now");
			add("Limited Homes Available Now");
			add("More Homesites Coming Winter 2022");
//			add("NEW SECTION COMING SOON");gfhj
			add("Final phase of lots now available");
			add("Opening in Mid-2023");
			add("New section of home sites now available");
			add("new homesites coming later in 2022");
//			add("NEW PHASE NOW OPEN|New Phase");
			add("FINAL OPPORTUNITIES REMAIN");
			add("Final 4 Homes Available Now");
			add("FINAL 4 HOMESITES REMAIN");
//			add("Available for Sale Now");
			add("multiple homesites available");
			add("Opening late summer 2022");
			add("Move In by Summer 2022");
			add("\\d+ Waterfront Cul-De-Sac Homesite Remains");
			add("\\d+ Townhomes already sold in new phase|\\d+ Townhomes already sold");
			add("3 new wooded cul-de-sac homesites just released");
			add("Walkout Basement Available");
			add("\\d Final Remaining Opportunities|Final Remaining Opportunities");
			add("Opening in early 2022|opening 2022");
			add("PHASE 1 AVAILABLE");
			add("One Home AVAILABLE");
			add("50% Sold Out in Phase I");
			add("Premium cul-de-sac lots available");
//			add("Limited wooded-view home sites available");
			// add("New Home Sites Released in our Final Phase");
			add("Coming Summer/Fall 2023");
//			add("Coming June 2022");

//			add("Coming Fall/Winter 2021");
			add("MORE THAN 300 HOMES SOLD");
			// add("over 90% sold out|Over 90% Sold Out|90% SOLD OUT");
			add("Only few lots remain|ONLY 1 Remaining Lot left");
			add("Phase 2 selling fast");
			add("Over 30 floor plans available");
			add("one lot remaining");
			add("Only 4 Left");
			add("Thirty Three Home Sites Now Available");
			add("60% sold out of Phase 1");
			add("66% of homesites sold");
			add("One home remaining before next phase");
			add("only 28 home opportunities");
			add("75% Sold Out");
			// add("Move-In Now Available");
			// add("Phase II Now Selling");
			add("few homesites left in the current phase");
//			add("Phase IV Coming Fall 2021");
			add("Phase 7B NOW OPEN");
			add("Quick Delivery Home Available");
			add("Phase 2 Opening Early 2022");
			add("Opening this Fall");
			// add("Opening Winter 2022");
			add("New Phase Coming Fall 2022");
			add("ONLY ONE AVAILABLE");
			add("PHASE 6 COMING SOON|New Phase Coming Fall 2022|FINAL TWO OPPORTUNITIES COMING SOON|Phase II Coming Soon|New homesites coming soon|Homesites Coming Soon|New Section Coming Soon|New Phase Coming this Summer|Grand Open Summer 2022|New Phase Coming Soon|New Phase Coming Summer 2022|Phase \\d coming summer|COMING THIS SUMMER| Lots Coming Soon|Basement lots coming soon|NEW PHASE NOW OPEN|New Phase|Coming Soon");
			add("First and Second Buildings SOLD OUT");
			add("Final Home Now Selling");
			add("Opening Spring 2022");
			// add("Phase 2 Opening this Summer|Phase 2 Opening Summer 2022|Phase 2 Opening
//			 Spring|Phase 2 Open");
			add("OVERSIZED WATER VIEW LOTS AVAILABLE");
			add("NEW SECTION NOW SELLING");
			add("over 85% sold out");
			add("0-down financing|0 down financing available");
			add("Limited number of homesites left");
//			add("FINAL PHASE");
			add("ONLY ONE LOT REMAINING");

			add("Coming mid fall");
			add("New Homes Coming Winter 2022|New Phase Coming Winter 2022|COMING Winter 2022");
			add("Coming Early Fall 2021");
			add("New Homesite Release Coming Soon");
			// add("Coming early 2022");
			add("move-in ready early 2022");
			add("New Phase Release coming late Fall");
			add("open early 2022");
			add("newest phase now open");
			add("Coming Late Fall 2022");
//			add("Opening Late 2021");
			add("Opening May 2022");
			add("New Wooded & Pond Homesites Available");
//			add("Coming mid-summer 2019");
//			add("Coming Winter 2019");
			add("New Homesites Releasing Soon");
//			add("Coming September 2018");
			add("Only 2 basement homes remain|Only Five Homes Remain");
//			add("Opening Late 2018");

			add("More than 60% sold");
//			add("NEXT PHASE COMING WINTER/SPRING 2021");
			add("FINAL 6 HOMES NOW SELLING");
//			add("Phase 2 Coming Winter of 2020-2021");
			add(" Phase 2- Closing Out");
			add("Only Four Opportunities Remaining");
//			add("New Floor Plans Available|New Floor Plan Available");
			add("Last Homes Available");
			add("Phases 2 and 3 selling now");
			add("Only a few homes remain");
			add("water front homesites available");
			add("Pond And Lakefront Homesites Available");
			add("Less Than 10 Homes Remain");
			add("Spring Move-Ins Available|Summer Move-Ins Available");
			add("Only 1 Ranch Home Remains");
			add("Last home available in Phase 3|Last Home Available");
			add("only inventory homes remain");
			add("Opening for Sale This Summer 2018");
			add("Final Section of Homesites Now Released|Phase 3 Home Sites Now Released |Final Sections Now Selling");
			add("Only 1 Opportunity\\s*Home Remains");
			add("Only 1 new home opportunity remains");
			add("Limited Homes Left|TWO HOMES LEFT IN PHASE 5|Just 1 Home Site Left|JUST THREE HOMES LEFT|Two Homes Left");
			add("Opening March 31st");
			add("Final 5 units available");
			add("Final home sites released");
//			add("Final Inventory Opportunities");
			add("Final Two Homes Available");
			add("Opening Late Spring 2022|OPENING LATE SPRING|Opening Late 2019|opening in the spring of 2019");
			add("Coming January 2018|Available Winter 2018|Coming First Quarter 2019");
			add("Now Selling Our Newest Section");
//			add("NEW SECTION AVAILABLE");
//			add("Final homesites available now|Final Homesites available");
//			add("Coming winter 2018");
			add("Only 9 New Homes Remain|ONLY THREE NEW HOMES REMAIN");
			add("Homesites With Lake Views Available");
			add("PHASE II Coming 2018|Coming December 2019");
//			add("open summer 2018");
//			add("final phase");
			add("MORE OPPORTUNITIES AVAILABLE");
//			add("New phase opening late 2018");
//			add("Only Two Home Sites Remain|Two Home Sites Remain");
//			add("Limited-Time Offer");
			add("ONLY FOUR HOMES LEFT");
//			add("Opening Beginning of 2018");
			// add("Available Inventory Home Only");
			add("Only 3 Grand Finale Home Sites Remain");
			add("8 Lots Remaining");
			add("ONLY 2 LAKE HOMES REMAINING");
			// add(" final phase|Final Phase");
			// add("JUST 2 homes left| 2 Homes Left");
			add("New Addition Just Released");
			add("only basement lots remain");
			add("ONLY \\d LOTS REMAINING|ONLY \\d LOTS REMAIN|1 LOT REMAINS|less than \\d+ lots remaining|\\d+ Lot[s]* Remaining");
			add("Final Few Homes Left");
//			add("Opening Spring 201[8|9]");
			add("Limited Basement Homesites Remain");
			add("Wooded & Walkout Lots Remaining|10 Lots Remaining");
			add("Only \\d+ home sites left|Only \\d+ Home Sites Left");
			add("Coming Jan 2019|Coming January 2019|Coming February 2019|Arriving Early 2019|Arriving Late 2022");
			add("[Only]*\\s*3 Lots Remaining");
			add("Only 18 large homesites available|Greenbelt view homesites available|Oversized/Greenbelt Homesites Available|oversize homesites available|two large homesites available|Large Homesites Available");
			add("last phase released");
			add("Only 40 total opportunities available");
			add("Only 73 Sites Available");
			add("Last phase");
			add("Coming this winter|Coming this fall|New Phase Coming This Fall|Phase 4 COMING SOON|Next Phase Release Coming February 2022|New Phases Coming 2022|Phase 4 Coming Mid Summer 2021|New Lots Coming Summer 2021|New Phase Coming Summer 2020|Phase 3 Coming Summer 2021|Next Phase Coming Summer 2021|Coming Summer 2021|New phase coming Summer 2021|New phase coming late summer 2020|New Phase coming late Summer/early Fall 2021|Phase 2 Coming Summer 2018|Phase Two Coming Summer 2018|Phase Two Coming Summer 2021|Coming Summer 2018|New Phase Coming Early 2021|Occupancy Early 2021|Spring 2021 Occupancy"); // Next
			// Phase
			// coming
			// Summer
			// 2022
//			add("New phase coming July 2018|COMING JULY 2018");
			add("New homes coming late summer|Coming late Summer");
//			add("Arriving Winter 2020|Arriving Fall 2019");
			add("NOW CLOSING");
			add("Available Inventory Home[s]* Only");
			// add("Move In Now");
			// add("Coming spring 2018");
			add("ONLY 1 LUXURY PATIO HOME REMAINS");
			// add("Limited Availability");
			add("ONLY FOUR LOTS REMAINING");
			add("final 3 homesites available");
			add("PHASE I 70% Sold");
			// add("Basement homesites available|BASEMENT HOMESITES AVAILABLE");
			add("ONLY 1 INVENTORY HOME REMAINING|Only TWO Waterfront Homesites Remain");
			add("LAST PHASE SELLING");
			add("NEW HOMES COMING IN EARLY 2022|PHASE 2 COMING EARLY 2018|New Homes Coming Early 201[8|9]|Coming Early 201[8|9]|COMING EARLY 2018");// |Early
				
			add("Coming In 2023");// 2018");
			// add("Opening May of 2017");
			add("\\d+ New Plans Available");
			add("Last Remaining Opportunity");
			// add("Phase 3 coming this spring|Coming this spring");
			add("Last Lots Released");
//			add("New Phase Summer 2018");
//			add("Phase 2 opening June 2018|Opening June 2018");
//			add("New Homes Available");
			add("FINAL NEW HOME OPPORTUNITIES");
			add("Two homes ready now");
//			add("Summer/Fall 2017|SUMMER / FALL 2017");
//			add("FALL/WINTER 2017|FALL / WINTER 2017");
			add("home sites currently available");
			add("USDA ZERO DOWN FINANCING AVAILABLE");
			add("70 home-sites available");
			add("Just 1 Lot Left");
			add("only a few residences remaining");
			add("JUST 3 homes left");
			add("150 LOTS REMAINING");
//			add("Opening Late Summer 2017");
//			add("TOWNHOMES ARRIVING 2020");
//			add("Move-in Spring 2018");
//			add("Opening February 2018|Opening February 2019");
			add("only 30 home sites");
			add("IMMEDIATE DELIVERY AVAILABLE");
			add("Only 1 Residence Available");
			add("ONLY 3 CHANCES LEFT");
			add("Only 7 units left");
			add("Lots are going fast");
			add("Designer Showcase Homes available");
			// add("Now Selling New Phase| Selling New Phase");
			add("Final 3 Homes");
			add("ONLY FEW REMAINING");
//			add("Final Phase");
			// add("NEARING SELL-OUT");
//			add("Final Homes Remain");
			// add("Opening New Phase");
			add("New Available Lots");
			add("last section for sale now");
			add("FINAL 50's Section");
			// add("95% SOLD|95% Already Sold");
			add("New townhomes coming mid 2020|Coming Soon March 2020|Coming Soon Early 2020|Coming Soon Summer 2020|Coming Soon May 2020");
			add("coming late 2022|New Section Coming Late 2022|New Homes Coming Late 2022|Phase 8 coming late 2018|Next Phase Coming Late 2021");
			add("MORE LOTS RELEASED|NEW LOTS RELEASED");
			add("Final Lots Released");
			add("Only 1�opportunity remains|Only one more opportunity");
//			add("\\d+ Homes Available");
			add("1 Townhome Homesite Remains");
			// add("new phase coming soon|New Phase Coming Soon");
			// add("Coming in Fall of 2018|coming Fall 2019 |Phase III coming late
			// 2020|Townhomes Coming 2019|Coming 2019|");
			add("Fall 2022 Limited Availability|Limited availability remains|limited availability remaining|Limited availability remain[s]*|Limited availability remains|Limited availability remains|LIMITED AVAILABILITY|Limited Availability|FINAL HOME REMAINING|FINAL HOME REMAINS");
			add("New Home Sites Just Released");
			add("New Homes Just Released");
//			add("New Section Coming soon");
//			add("Last Opportunity");
			add("Limited Home Opportunities");
//			add("New townhomes coming mid 2020");
			// add("Now Selling New Section");
//			add("");
			add("Only \\d+ New Home Lots Left");
			add("Only 2 Remaining Opportunities|Only 2 remaining");
			add("Final Homes Remaining|Final Phase Released|Final Phase Release|Final Phase Releasing Soon|Final Homes Remain");
			add("8 Homesites Remaining|Limited Homesites Remaining|Limited home sites remain|limited homesites remaining|Limitied homesites remain|Home sites limited|Limited Homesites Remain|LIMITED HOMES REMAINING|Limited Homes Remain|Limited Homes Remain");
			add("Coming Spring 2024|Townhomes coming Spring 2022|new phase homesites coming Spring 2022|COMING SPRING 2022|Arriving Fall 2022|Arriving Fall 2023|Arriving Summer 2023|Arriving Spring 2023|Arriving Early 2022|New Phase Coming this Spring|Arriving Summer 2022|Arriving Spring 2022|New Phase Coming Spring 2022|Coming soon final Phase|PHASE 2 COMING SPRING 2018|PHASE 3 COMING SPRING 2021|NEW HOMES COMING SPRING 2018|NEW PHASE COMING SPRING 2020|Phase II Coming Spring 2018|Coming Soon Spring 2020|Coming Spring 201[8|9]|Coming Spring 2020");
			add("only two lots left|Limited lots left|TWO LOTS LEFT");
			add("NOW OPEN Phase 2|Phase II Open Now");
			add("new section coming mid 2022|New Townhomes Just Released|Just released|New Wooded Home Sites Just Released|New Phase Homesites Just Released|New Building Just Released|Final Home Sites Just Released|Wooded and walkout basement sites just released|FINAL PHASE OF PRESERVE HOMESITES JUST RELEASED|Phase II Homesites Just Released|New Basement Homesites Just Released|Homesites Just Released|HOMESITES JUST RELEASED|FINAL PHASE JUST RELEASED|Final homesites just released|just released final phase|Final Section Just Released|New home sites released|New Home Site Just Released|Final home site just released|New Phase and Plan Just Released|New Section Just Released|Phase 2 just released|New Homesite Release|Last Phase Just Released|New Homesites Released|Newly Released Home Sites|NEW LOTS JUST RELEASED|\\d+ Wooded Homesites Remaining|wooded homesites just released|NEW HOMESITES JUST RELEASED|Last Section Just Released|New phase just released|New Phase Just Released|New Phase Released|New Phase Release|New phase just opened");
//			add("coming spring 2022");
			add("GREENBELT Home sites Available|Final homesites available now|Final Homesites available|Limited wooded-view home sites available|Phase-II \\d+ homesites available|few home sites available|Walkout home sites available|Cul-de-sac Homesite Remains|Phase II 21 homesites available|new phase homesites available now|Lakefront and wooded home sites available|\\d+ Wooded Homesites Available|Wooded Homesites Available|wooded homesites available|Wooded and Basement Home sites available|Only 26 homesites available|Premium Home sites available|Final phase homesites available now|Basement Home sites Available Only|lakeside and bayfront homesites available|phase 2 homesites available|CUL-DE-SAC AND POND HOMESITES AVAILABLE|\\d+ cul-de-sac homesites remaining|cul-de-sac homesites available|Lakeview and cul-de-sac homesites available|Lakeview Homesites Available Now|Lakeview Home Sites Available|waterfront home sites available|Large Home Sites Available|Basement Home sites Available|Limited Lake[front]* Homesites Available|Lakefront Home Sites Available|Only 10 Grand Finale Home Sites Remain|Phase One Homesites available|remaining home sites available|NEW homesites available now|NEW HOME SITES AVAILABLE NOW|New Home Sites Available|450 homesites available|Final Home Sites Available|Oversized home sites available|86 foot home sites available|Home Sites Available|new homesites available|limited home sites available|New Sites Available|only \\d+ home sites available|\\d+ home sites available|new homesites available|only five homesites available|only four homesites available|only \\d+ homesites available|\\d+ homesites available|Only a few homesites available|Limited Homesites Available|LIMITED HOMESITE |New Phase Homesites Available|Limited Homes Available|homesites available NOW");
//			add("Phase 2 Coming in 2017");
			add("Selling out soon");
			// add("Homesites Coming Soon");
//			add("Coming Early Summer 2018|New Phase Coming Late Summer 2018|Coming Late Summer 2019|Coming Early Spring 2017|Coming Early Winter 2017|COMING LATE SUMMER 2018|New Phase Coming Summer 2017|Coming soon early 2017|Coming early summer 2019|coming in summer 2017|coming Summer 2020 |Coming Summer 2020|Coming Late Summer 2021|Coming Early 2017|Phase 2 coming early 2017|released early 2017");
//			add("Coming Fall of 2019");
			add("Only a Few Lots Left");
			// add("Opening in the Fall");
			add("Coming Fall 2024|New Phase 2022|Phase Two Coming Fall 2018|New Opportunities Coming Fall 2018|Coming in the fall of 2018|Second Phase Coming Fall 2018|Townhomes Coming Fall 2018|Final Phase Coming Fall 2018|Coming Early Fall 2019|Coming Fall of 2018|NEW PHASE COMING FALL 2020|Townhomes Coming in 2019|Coming Feb 2019|coming March 2018|Coming April 2018|New Phase Coming 20\\d{2}|New Phase Coming 2018|Phase 6 coming later 2021|Townhomes Ready Summer 2021");
			add("Opening Summer 2023|Phase 2 Opening this Summer|Phase 2 Opening Summer 2022|Phase 2 Opening Spring|Phase 2 Open|Grand Open Fall 2022|Opening mid 2022|New Phase Opening Fall 2022|opening Fall 2022|OPENING THIS SUMMER|GRAND OPENING|Grand Opening in Early 2019|Grand Opening Summer 2021|Grand Opening Early 2020|Arriving Summer 2020|Arriving Early 2021|Arriving Spring 2021|Phase 3 opening Fall 2019|opening early 2019|Opening Early 2019|Targeted Grand Opening October 2018|grand open spring 2018|Opening Winter 2017-2018|Opening November 2018|Opening September 2018|opening  september 2018|Opening August 2018|Opening October 2018|Opening December 2018|Opening August 2018|Opening Early Fall 2018|Opening this Fall 2019|Opening March 2020|opening mid 2017|Open Spring 2020|Opening Early 2020|open in Spring 2018|Opening Spring 2018|Opening Winter 2020|opening Fall 2018|Opening October 2019|Opening 2019|Opening Spring 2021|Opening Summer 2021|NEW PHASE OPEN");
//			add("Only \\d homesites left");
//			add("Coming Spring 2021");
			add("Only TWO opportunities available");
			add("Only One Opportunity Remains in Phase 1|Only 1 More Opportunities|only one opportunity remains|Only ONE opportunity remains|only one opportunity remains|Only One Opportunity Remains|Just One Opportunity Remains|One Opportunity Remains");
//			add("One Quick Move-In Home Remains")
			add("ONE OPPORTUNITY LEFT");
			add("Only 6 Opportunities Left|Only \\d+ Opportunities Left|13 OPPORTUNITIES LEFT|Just 8 opportunities left|\\d+ OPPORTUNITIES LEFT|ONLY A FEW OPPORTUNITIES REMAIN|ONLY A FEW OPPORTUNITIES LEFT|Just few opportunities remain|FEW OPPORTUNITIES REMAIN|few opportunities left|only a few opportunities remaining|Only One Opportunity Left");
			// add("Only four homes remain");
			add("only 21 opportunities available");
			add("ONLY 14 OPPORTUNITIES");
			add("ONLY A COUPLE OPPORTUNITIES LEFT|Only a couple homes left");
			add("Only a few home sites remain|few home sites remaining in phase 1|few home sites remain");
			add("Only 5 homesites left");
			add("Phase 1 almost completed");
			// add("New Homes Coming Soon");
			// add("New townhomes coming soon|New Townhomes Coming Soon");
			add("Only 2 Homes Remian|Last home remaining");
			add("NEW PHASE RELEASING SOON");
			add("FINAL PHASE CLOSEOUT");
			add("FINAL PHASE NOW OPEN");
			// add("Limited Homesites");
			add("ONLY 5 OPPORTUNITIES REMAIN");
			add("Final Opportunity Remaining");
			add("Only 1 Opportunity Remaining|Phase 2 Final Opportunity|Final Opportunity Phase One|Phase 2 Final Opportunities|Final Opportunities in Phase 1|Final Opportunities Coming Late 2022|Final Opportunities in Current Phase|Final 3 Opportunities Remain in Current Phase|Final Opportunities 3 Homes Left|Final Opportunities |Last Opportunity Section 4|Final Opportunities in Phase 3|One final opportunity remaining|Closeout Final Opportunities|Final Opportunities Available NOW|Final Opportunity Available|Limited final opportunities remain|FINAL OPPORTUNITIES AVAILABLE|Final Opportunities remaining in current phase|Final Opportunities Remaining|Final Opportunities Remain|FINAL OPPORTUNITIES LEFT|only 1 FINAL Opportunity Remaining|Final Opportunities Remaining|Final 3 Opportunities|\\d Opportunity Remaining|LAST OPPORTUNITY REMAINING|One Final Opportunity Remains |Final Opportunity Remains|\\d Final Opportunities|final opportunities|Final Opportunties|FINAL OPPORTUNITY|Final Opportunity|Final Opportunity|FINAL OPPORTUNITIES|Final Opportunities|Last Opportunity|Final Opportunities|Final \\d+ Opportunities");
//			add("Final Opportunity Phase One");
			// add("New homes coming soon 2022");
//			add("Late Summer 2022");
			add("new homes coming soon 2023|COMING SOON  2023");
			add("Phase 9 Coming Soon");
//			add("Basement lots coming soon");
			add("Next Phase Coming Soon");
//			add("Next Phase Coming Mid 2022");
			// add("Coming Soon Spring 2023|Coming Soon Winter 2023|Coming Soon Fall 2022");
//			add("COMING SOON IN MID 2022");
//			add("New Section Coming Soon");
//			add("Coming Soon|New homesites coming soon|New Section Coming Soon");
//			add("New Section Coming Soon");
			// sauuuuuu
//			add("New Phase Coming Fall 2022|New Home Sites Coming Fall 2022|Future Phase Coming Fall 2022|New homes coming soon 2022|COMING Fall 2022|New phase coming soon early 2023|COMING SOON 2022|new homesites coming in 2022|New Homes Coming Fall 2022|New Opportunities Coming Soon|Section II Coming Soon|Coming Soon Summer 2022|NEXT COLLECTION COMING SOON|New Homes Coming Soon|New lots coming soon|Coming in 2023|Coming Soon 2023|60' Homesites Coming Soon|New 70' Homesites Coming Soon|New Phase Coming Early 2022|Coming Soon Spring, 2022|Coming Soon Mid 2022|Coming Soon Summer 22|COMING SOON SUMMER 2022|Coming Soon Spring 2022|FINAL TWO OPPORTUNITIES COMING SOON|NEW TOWNHOMES COMING IN 2022|New Homesites Coming Soon|New phase homesites coming soon|New Homesites Coming Fall 2022|New Homesites Coming Early 2022|Coming Fall 2022|Coming early 2022|New Homes Coming Soon|COMING JANUARY 2022|Coming in 2022|MORE HOMES COMING SOON|Phase 2 coming soon|homesites just opened|Phase 5B COMING SOON|New Town Homes Coming Soon|Next Phase Coming Soon|Next Phase - Coming Soon|coming soon Winter 2019|Coming Soon Spring 2019|Phase II Coming Soon|NEXT PHASE COMING SOON|Phase III Coming Soon|new phase coming soon|Final Home Coming Soon|Final Release Coming Soon|New oversized home sites coming soon|Coming Soon Early 2018|Coming Soon Summer 2018|Coming Soon Spring 2018|COMING SOON 2018|Phase 7 COMING SOON|Coming Soon January 2018|Coming Soon Late 2017|Phase 2 and 3 coming soon|Wooded Home Sites Coming Soon|Final section coming soon|More Home Sites Coming Soon|NEW PHASE Coming Soon|New Phase Coming Soon|Coming Soon 2017|Coming Soon Spring 2019|Coming Soon Early 2019|New Designer Phase Coming Soon|COMING SOON JUNE 2017|Coming Soon- FALL 2017|MORE OPPORTUNITIES COMING SOON|NEW RELEASE COMING SOON|COMING SOON JUNE 2017|New plans & new homesites coming soon|Homesites Coming Soon|COMING SOON APRIL 2019|Additional phase coming soon|Coming Soon Summer 2017|Coming Soon in 2018|Coming Soon Fall 2018|New Townhomes Coming Soon|Second Phase Coming Soon|NEW PHASE COMING SOON|Final [P|p]hase Coming Soon|NEW LOTS COMING SOON|NEW HOMESITES COMING SOON|New home sites coming soon|New Phases Coming Soon|new phase coming soon|new phase coming soon|New Section[s]* Coming Soon|New Phase Coming Soon|Phase 3 Coming Soon|Phase 4 Coming Soon|Phase 6 Coming Soon|Homesites Coming Soon|Coming Soon in May|New homesites coming soon|Coming Soon|Coming Soon|COMING SOON|coming soon|Coming Soon|COMING SOON\n|New Homesites and Floor Plans Coming Soon"); 
			add("quickly selling out|Phase II selling out|Final Phase Selling Out Fast|selling out fast|first phase selling out|Lots selling out|Phase 1 selling out|Selling out quickly|Selling Out Quickly|Phase I selling out|Selling Out|Nearing sellout|NEARING SELL-OUT");
			add("Coming Soon Winter 23");
			add("Phase II opening July 2022");
//			add("\\$\\d Down Payment");
			add("GRAND RE-OPENING");
			add("Only 1 House Left");
			add("Selling out soon");
			add("Phase 5 now open");
			add("Selling Phase V");
			add("10 Final Home Sites Left");
			add("15 lots available");
			// add("only one homesite remaining|Only one homesite remains|ONE homesite
			// remaining |Only 6 new home opportunities remain");
			add("Cul-De-Sac Lots Available|Lakeview Lots Available|Pond and cul de sac lots available|pond and wooded lots available|lots available Summer 2022|Only 17 Lots are available|Final lots available|Lake\\s*front lots available|Waterfront and waterview lots available| wooded lots available|Just 1 Beautiful Lake Lot Available|premium lots available|Basement lots available|Oversize Lots Available|Mountain view lots available|Only 8 lots available|Water View Lots Available|Limited lot availability|Limited Lots availability|LIMITED LOTS AVAILABLE|Large Lots Available|New Lake View Lots Available|Water Front Lots Now Available|Water Lots Available|Only two lots available|Oversized Lots Available|Wooded & Pond Lots Available|Walkout basment lots available|Bay Front Lots Now Available|2 Opportunities Available|New Basement Lots Available|Waterfront Lots Available|New Lake Front Lots Available|New Lake Lots Available|Limited Lake Lots Available|New Greenbelt Lots Available|Greenbelt Lots Available|ONLY 1 Lakeside Lot Remaining|Over-Sized Lots Available|Limited homes and lots available|ONLY 18 Available Lots left|New Lots Available|14 available lots|Only one lot available|Phase 2 and 3 lots now available|\\d LOTS AVAILABLE|Large Lots Now Available|Wooded lots NOW AVAILABLE|lots are now available|32 Lots Now Available|lots now available|New lots now available|Oversized & Lake Lots Available|Lake Lots Available|Lots Available Now|Lots Available| Lots Available|only \\d+ lots available|\\d{2} Lots Available|Oversized Lots Still Available in New Section|view lots available|Cul-de-sac and oversized lots are available");
			add("new unit now open");
			add("Cul-de-sac and oversized lots are available");
			add("ONLY 8 TWIN HOMES LEFT");
			add("LAST HOME LEFT");
			add("limited opportunities in phase ii|limited opportunities remaining|Limited Presale Opportunities Now Available|Limited Opportunities Coming Soon|Limited Opportunities in Current Phase|43 opportunities available|Limited opportunities in Phase IV|\\d+ opportunity available|ONE OPPORTUNITY AVAILABLE|Water View Homesites Available|Only 18 Large Homesites Available|Last Opportunities Remain|LAST OPPORTUNITIES|Limited Opportunities Phase IV left|Limited Opportunity Remaining|Limited opportunities left|Only Few Opportunities Available|Only 1 Opportunity Left|1 Opportunity Left|One opportunity remaining|only five homesites available|Only two opportunities remain|Two opportunities remain|Only 2 basement opportunities remain|Only 2 opportunities remain|Only \\d+ Opportunities Remain|Limited Opportunities Available|limited build opportunities remaining|Limited Opportunities Remain|Limited Opportunities|LIMITED OPPORTUNITIES REMAIN|Limited Opportunities|Limited Opportunity");
			add("Only TWO opportunities available");
			// add("Only 1 Opportunity Remaining|Only 1 Opportunity Available|Only \\d
			// opportunities available");
			add("Only 5 Opportunities Remaining");
//			add("Only 1 Opportunity Left");
			add("4 patio home opportunities");
			add("Phase 2 is now available");
			add("Available Spring 2019|Available summer 2019");
			add("PHASE TWO NOW OPEN");
			add("Only 4 Homes Available");
//			add("New Phase Summer 2022");
//			add("Coming Soon December 22");
			add("LAST HOME LEFT");
			add("only 7 left");
			add("HOMES ARE SELLING FAST|Fast Selling Homesites|Lots are selling fast|lots are selling|lots selling fast|Phase I Selling Fast|NEW SECTION SELLING FAST|Final phase home sites selling fast|Final Homes Selling Fast|current phase selling fast|Phase III Selling Fast|Phase 1 selling fast|New Homes Selling Fast|HOMES SELLING FAST|Phase 4 Homesites Selling Fast|Homesites selling fast|Final Phase Selling Fast|Selling Fast|fast selling");
			add("Coming Late Spring 2023|Coming Late Spring 2022|Coming Late Spring 2019|Coming late Spring 2019|COMING LATE SPRING 2019|NEW PHASE COMING LATE SPRING|NEW SECTION COMING LATE SPRING|COMING LATE SPRING");
			add("Only 11 Sites Remain");
			add("New Floor Plans Now Available|New Floor Plans Available");
//			add("Only 6 Opportunities Left|Only \\d+ Opportunities Left|13 OPPORTUNITIES LEFT");
//			add("9 Opportunities left");
			add("Closing Soon");
			add("Only ONE home site remains|One home site remains");
			add("Just 5 Lots Remain|limited lots remaining");
			add("6 new home sites remain");
			add("Only 3 New Homes Remain");
			add("USDA Loans available");
			add("100% USDA financing available|100% USDA Eligible|USDA 100% financing available|USDA 100% Financing|USDA Rural Housing 100% financing|USDA Eligible 100% Financing|100% financing from usda|USDA Financing Eligible|USDA Eligible Financing|USDA-Financing Eligible|USDA Eligible|USDA 100% Financing Available|USDA Financing available|100% Financing Available|100% USDA financing|USDA financing|100% Financing|100% Financing|USDA 100% Financing|100% financing|USDA Home Loan");
			add("Only two homes remain");
			add("26 homesites with basements available|finished basements available|\\d Basements sites remaining|ONLY ONE HOMESITE AVAILABLE|walk out basement available|Basement Homesite Available");
			add("only 1 quick move in home remaining");
			add("NOW  OPEN");
			add("ONLY 1 LOT REMAINING|only \\d+ lots left|\\d{1,2} lots left|ONLY 4 EXCEPTIONAL LOTS LEFT|Last Lot Remain");
			add("Only 4 Quick Move In Homes Remain");
			add("Only three homesites remain|ONLY HOMESITE THREE REMAINS|5 Homesites Remaining in First Section|Less than 10 homesites remaining|Only three Homesites Left|3 Homesites Remaining|3 Homesites Remain|only \\d+ homesites remain|Only \\d+ homesites remain|Only 5 Homesites Remain|Less than 25 homesites remaining|\\d+ HOMESITES REMAIN");
			add("Only FIVE HOMES remaining");
			add("Only a few lots left");
//			add("grand opening soon|Grand Opening Soon|Opening late 2023|Phase II opening late summer|Grand Opening Summer 2022|opening Summer 2022|Opening April 2022|Grand Opening|Grand Opening Spring/Summer 2022|OPENING LATE SUMMER|Next Section Opening Soon|GRAND OPENING NEW PHASE|Phase \\dA Grand Opening|NEW PHASE GRAND OPENING|Grand Opening April 18|hand full of homes left in Phase II| New phase grand opening|Grand Opening April 18 2020|New Section Grand Opening|Grand opening late Spring of 2018|Grand Opening February 9|Grand Opening January 19|New Phase Grand Opening Now|Grand Opening Late Summer 2018|Grand Opening of Final Phase|Grand Opening May 2018|GRAND OPENING PHASE 2|GRAND OPENING Phase 4|Opening May 2018|GRAND OPENING PHASE 3 & 4|Grand Opening April 2018|Grand Opening in May 2018|Grand Opening Phase II|Grand Opening in early 2018|Grand Opening April 7, 2018|Grand Opening early 2018|Opening early 2018|Grand opening late 2020|Grand Opening August 2018|Grand Opening October 2018|Grand Opening This Summer|Phase 2 Grand Opening|Grand Opening September 2018|GRAND OPENING JUNE 18|Grand Opening January 2019|Grand Opening Summer 2018|open Summer 2018|Open Fall 2018|Opening Summer 2019|Grand Opening March 2018|grand opening early 2018|Opening Early 2018|Grand Opening Soon|GRAND OPENING PHASE 2|Grand Opening this fall|Opening Late Spring 2019|Opening Early Summer 2019|Opening Late 2020|Opening Late 2022|Opening Early 2022|Opening March 2018|Grand Opening Opportunities|Grand Opening Section 5|GRAND OPENING JANUARY 25TH AND 26TH|Grand Opening January 25th|New Phase Grand Opening|Grand Opening New Section|GRAND OPENING OF PHASE 3 & 4|GRAND OPENING IN JUNE|Grand Opening \\w* 2019|Grand Opening");
//			add("Only one lot left");
			add("New phase	coming soon");
			add("Cul-de-sac lots and water views available");
			add("only 1 house remaining");
			add("\\d+ town homes remaining");
			add("USDA Approved Financing Available");
			add("Only 1 Homesite Left");
			add("Only \\d Townhome Remains|Only 1 Townhome Left");
			add("FINAL PHASE NOW OPEN");
			add("Final Phases Now Selling");
			add("Last 5 Homes Remaining");
			add("Opening Spring 2020");
			add("PHASE 1 -SOLD OUT");
			add("PHASE 2 - NOW SELLING");
			add("ONLY TWO DESIGNER HOMES REMAINING");
			add("ONLY 2 HOMESITES LEFT");
//			add("LIMITED TIME OFFER");
			add("Currently selling NEW SECTION|Currently Selling Phase 6|currently selling Phase 1|Currently Selling");
			add("ONLY 1 MARKET HOME REMAINING|ONLY 6 MARKET HOMES AVAILABLE|Only 5 Market Homes Remaining|Only 6 new market homes left|Only 1 Market Home Remains");
			add("[Only]*\\s*\\d+ Townhomes Remaining");
			add("ONLY 2 INVENTORY HOMES REMAINING");
			add("Two Stunning Homes Available|Only 1 Available Home Left|ONLY 2 AVAILABLE HOMES LEFT|only a few available homesites left|\\d+ Available Homesites|\\d+ Available Homes|New homes available soon|\\d+ Home Available Now|\\d+ Homes Available Now|Only 1 Home Available|Basement Homes Available|Last Two Homes Now Selling|Last Two Homes Available|\\d+ available homesites |only \\d+ homes available|\\d+ homes available");
			add("Final Homesites Now Selling");
			add("Phase 5B opening this winter|Opening This Winter|OPENING NOVEMBER 1st|Opening Winter 2022");
//			 add("Now Selling");
			add("Grand Finale on Phase One");
			add("Final 7 Homesites Remaining");
			add("Just 1 home remains");
			add("CLOSEOUT OPPORTUNITIES");
			add("COMING THIS September");
			add("Final Phase Coming This Summer|NEW HOMESITES COMING THIS SUMMER|NEW SECTION COMING THIS SUMMER");
			add("Only 1 Homesite Available|ONE NEW HOMESITE AVAILABLE");
			add("Only 3 \"Quick Move In\" homes remaining");
			add("Grand Closing");
			add("ONLY A FEW REMAINING LOTS|few remaining lots");
			add("100% Financing Now Available");
			// add("Only 1 Available Home Left|ONLY 2 AVAILABLE HOMES LEFT");
			add("Ony \\d+ Homes Left|Only Three New Homes Left|2 homes only|Only \\d+ Homes Left|only 4 homes left|only 3 homes left|4 HOMES LEFT|ONLY 3 HOMES LEFT|2 HOMES LEFT|5 Homes Left");
			add("PHASE 3 NOW AVAILABLE");
			add("current phase two home sites remaining|two home sites remaining|TWO home sites remaining|two home sites remaining");
			// add("Ready to move in|READY FOR MOVE IN|Ready for Summer Move-in");
			add("Phase II Coming Early 2023|New Phase Coming Early 2023|Coming Early 2023|New Homes Coming Early 2023|New Section Coming Early 2023|Phase 2 Coming Early 2020|Phase I available Spring 2020|Coming Early 2020|coming early 2020|Coming March 2020|TOWNHOMES COMING 2020|Coming January 2020|New lots coming 2021|COMING 2021|Coming 2020");
			add("Less than 60 home sites remaining|LESS THAN 20 HOME SITES REMAINING|ONLY FOUR HOMESITES REMAIN|Grand Finale Only 4 Home Sites Remain|only 138 home sites|\\d+ home sites remaining|Only \\d+ home sites remaining|Only \\d+ home sites remain|Only 26 home sites remain|Only 2 Home Sites Remaining");
			add("second phase coming this fall");
			add("Coming Later this Year");
			add("168 Homesites Released In Sections");
			add("Phase 2 Homesites Now Available");
			add("NEW 65' OVERSIZE HOMESITES NOW AVAILABLE");
			add("NEW CLASSIC HOME SITES AVAILABLE");
			add("ONLY NINE OPPORTUNITIES LEFT");
			add("29 TOWNHOMES STILL AVAILABLE");
			add("New Home Ready Now");
			add("Over 100 Homesites Sold");
			add("TWO HOMESITES REMAINING");
			// add("a few homes remaining|Only a few more homesites remain");
			// add("TEMPORARILY SOLD OUT");
//			add("Final Phase coming in 2020");
//			add("PHASE 1 SOLD OUT|Phase 1 SOLD OUT");
			add("New Townhome Available|only 24 townhomes available|LAST 3 HOMES AVAILABLE");
			add("Coming in September|New Section Coming in September");
			// add("TWO HOMES REMAIN");
			add("Only TWO homesite remains");
			add("phase 2 opening soon|New Phase Opening Soon|New Phase Opening January 2020|NEW PHASE OPENING THIS FALL|Opening in October|New phase opening in 2020|New Section Open Now|New section opening Spring 2019|new section opening soon|New Section Open|Section 3 Opens Soon|new phase opening soon|New Phase Open Now");
			add("only 1 homesite left");
			add("Just 1 homesite available");
			add("Closing Out - Few remaining");
			add("New Phase Closeout|community closeout|section 6 closeout|CLOSEOUT COMMUNITIY|closing out the final phase|Phase 2 closeout|PHASE ONE CLOSE-OUT|Phase 1 Closeout|Current Phase Closeout|Closing out current phase|Grand Closeout|Nearing Close-out|Phase I Close-Out|closing out soon|Closeout Community|Community Closeout|Nearing Closeout|Phase Closeout|FINAL CLOSE OUT|FINAL CLOSE-OUT|Nearing Close Out|Near Close Out|Close Out Opportunities|ALMOST CLOSED OUT|Close Out|CLOSEOUT|Final Closeout|CLOSED-OUT|CLOSED OUT |Close-Out Special|Close Out Specials| Close-Out?|Close-out |Close-Out|close-out |CLOSING OUT|closing out|Close-Out");
			add("0% Down Available");
			add("Unit \\d Now Open");
//			add("Final Home Available");
			add("TWO LOTS REMAIN|Three Opportunities Remaining");
//			add("Only 7 Town Homes Available|2 remaining quick delivery homes available|Quick Move-in Homes Coming Soon|Quick Move-In Homes Ready Now|New Homes Ready Now|Homes Ready Now|Only 2 Move-In Ready Homes Remain|1 QUICK MOVE-IN HOME LEFT|No Quick Move In Homes|No Quick Move-In Homes|Immediate Move-ins Available|MOVE-IN READY|One Move-In Ready Home Remains|Quick Move-In Homes|Quick Move-In Home|Quick Move In Homes|Quick Move-In |1\\d Quick Move-Ins Available|\\d+ Quick Move-In Home[s]*|Quick Move-ins Available|\\d+ Quick Move-in home|No Quick Move-Ins Available|no Quick Move-In homes available|Immediate Move-In Available|no Move-In Ready Homes|No Move-In Ready Homes|No Move-In Ready Homes|no move-in ready homes available|\\d Quick Move-ins? Available|Quick Move-ins? Available|No Quick Move-In homes available|Quick Move-in not Available|Quick Movein not Available|No Quick Move-In|QUICK MOVE-IN HOMES|No Quick Move-In Homes|Move-In Ready Available|Quick Delivery Homes|Quick Delivery Home|Quick-Move In Homes| Quick Move-in");
			add("Now Offering Premier Floor Plans");
			add("Ready for Sale");
			add("Not Ready for Sale");
			add("Not Ready to sale");
			add("Final Two Homes");
			add("Phase I currently sold out|Over 95% sold out|over 50% sold|over 90% sold out|Over 90% Sold Out|90% SOLD OUT|PHASE SOLD OUT|Over 70% sold out|Phase 1 Currently Sold Out|Currently Sold Out|phase i and phase ii sold out|Sold Out Fast|first phase sold out|over 80% sold out|Current Phase Sold Out|Phase one sold out|Phase 1A is SOLD OUT|Phase 2 is sold out|currently sold out Phases 1|Phase 1 is SOLD OUT|Currently Sold Out Phase 1|Current Phases SOLD OUT|two-thirds sold out|50% sold out of final phase|50% Sold Out in Phase I|Current phase sold out|Phase 1 SOLD OUT|Over 80% Sold-Out|Phase 8 SOLD OUT|Phase 4 nearly sold out|Sold out Phase I|phase one and phase two sold out|MORE THAN 70% SOLD OUT|first phase sold out|First Phase Over 75% Sold Out|Over 75% Sold|Phase 7 SOLD OUT|Current Section Sold Out|CURRENT PHASE SOLD OUT|currnetly sold out|Phase II SOLD OUT|sold out quickly|Last Phase Now Selling|last phase sold out fast|Phase 1 nearly sold out|Lots are sold out|sold out current phase|phase 1 almost sold out|Phase One Is Almost Sold Out|Lots Sold Out|currently Sold Out|Currently Sold Out|Currently Sold|PHASE 3 SOLD OUT|ALMOST SOLD OUT|almost sold out|Almost Sold Out|Almost Sold Out|almost sold out|Phase I SOLD OUT|Phase I sold out|Phase I is SOLD OUT|Sold Out|SOLD OUT|SOLD OUT|Nearly sold out|sold-out|TEMPORARILY SOLD OUT|temporarily sold out|50% SOLD");
			// add("Current Phase Sold Out");
			add("FINAL PHASE LAST CHANCE|Last Chance|LAST CHANCE|Last chance|FINAL CHANCE");
			add("only a few more oppportunities available");
			add("Leasing Summer 2019|Leasing Spring 2019");
			add("\\d+ Homes Already Sold|13 Homes Sold|58 homes sold|\\d+ homes sold|Over \\d+ homes sold|80\\+ Homes Sold");
			add("Only 11 Homes Remaining|Only \\d Home Remaining|just a few homes remain|Final 6 Homes Remaining|Final phase - One home remaining|Only One Remaining Home|One home left in current phase|1 Home Site Remains|One Homesite Left|only one home left|Only three homes remain|Only a Few Homes Remain|Under 10 homes remain|Only \\d{1,2} Townhomes Remain|Just ONE home left in Phase 1|Just one lot left|Only 1 home remain[s]*|FINAL FEW HOMES REMAINING|Less Than 100 Homes Remain|Only 1 Move-in Home Remaining|Less than 10 homes remaining|Less than 25 Homes Remaining|Just 9 Homes Remain|\\d+ Homes Remaining|\\d+ Home Remaining|Only ONE home available|only 2 homes left|Only 1 home left|only 1 home left|ONLY ONE HOME LEFT|1 HOME LEFT|One Home Left|Only one townhome remains|One Townhome Homesite Remaining|ONE homesite remaining|ONE home remaining|Only 1 Home Remaining|Only \\d Homes remaining|Only 6 Homes remain|Only \\d+ [h|H]omes [r|R]emain|One Home Remains|Only�\\d Homes Remains?!?|ONLY�1 HOME REMAINS!?|Only \\d Homes remains|\\d+ HOMES REMAIN|Only 1 Home Remain|ONLY 1 HOMESITE REMAINS|\\d+ Oversized Homesite Remains|ONLY 1 HOME REMAINS|only one home remaining|one home remaining|1 Homesite Remains|1 Home Remains|Few homes remain|Final Homesite Remains");// \\d+
																																																																																																																																																																																																																																																																																																																														// Home
//			add("Coming Winter 2023");																																																																																																																																																																																																																																																																																																																											// Remaining
			add("Homes now available in Phase 4");
//			add("New Phase Now Selling");
			add("only 1 lot left|Final Few Lots Remaining");
			add("Only \\d opportunity remains|just a few homesites remaining|Only A Few Homesites Remaining|8 homesites left in Phase 1|only one homesite remaining|only one home site remaining|Only one homesite remains| 4 homesites left in current section|few homesites left in phase 5|Only few home sites left|ONE homesite remaining |1 Opportunity Remains|Only 6 new home opportunities remain|ONLY TWO HOMESITES REMAIN|Final Homesites Remaining|FINAL HOMESITES REMAIN|ONE HOMESITE REMAINING|Few Homesites Remaining|Only Few Homesites Remain|Only a Few Remain|Only a few remaining homes|Only a few new homes remain|ONLY 7 HOMESITES LEFT IN PHASE 7|3 remaining homesites|Only a Few Homesites Left|Only One Homes Remains|Only one homesite left|three homesites remaining|Only A Few Homes Left|Only \\d Homesites Remaining to Choose From|Just few homesites remain|Few Homes Sites Remain|Only 1 homesite remaining|Only 1 Homesite Remaining |1 Home Site Remaining|ONLY 1 HOMESITE REMAINING|\\d+ HOMESITE remaining |\\d Homesite Remaining|One home site remaining?|1 Home Site Available|Final Home Site Available|ONE home site remaining|one home site remaining|Few Homesites Remain|Only a few homesites remain|Oversized and waterfront homesites available|Waterfront Homesites Available| One Wooded Homesite Available|Only three homesites available|Only \\d homesites left|\\d+ HOMESITES LEFT|Last Homesite available|few homesites left|Few Homes Left|One Homesite Remains");
			add("USDA LOAN QUALIFIED|FHA FINANCING AVAILABLE");
			add("1 Home opportunity left|Final New Home Opportunity|Final Home Opportunity Phase I");
			add("New Phase Now Available|New Section Homesites Now Available|New Home Now Available|New homesites are now available|new phase homesites now available|Final Section Townhomes Now Selling|Now selling in Phase 4|New Homes Now Open|Phase II Final Lots Now Selling|NEW SECTION NOW OPEN|NOW SELLING NEW SECTION|Phase 9 Now selling}New section of home sites now available|Homes now available in Phase 4|Homes available now in Phase 2|Homes now available in Phase 2|Phase VI now open|Now Selling in Phase III|NEW and FINAL PHASE NOW AVAILABLE|Now Selling Phase IV|Lakefront and lake-view homesites are now available|FINAL OPPORTUNITY NOW SELLING|Homes Now Available|Now Selling Final Section|Lake View Oversized Homesites Available|Now Selling New Section|Phase II Now Selling|Oversized Homesites Available|First Phase Now Selling|Phase 2 Homes Now Available|Section \\d Now Selling|NOW SELLING PHASE \\d|Now Selling New Sections|Now Selling New Phase|NEW PHASE NOW SELLING|New Phase Now selling|phase 5 homes selling now|50 homesites now available|PHASE 6 NOW OPEN|Now Selling Final Phase|Phase 3 now selling|Now selling new homes|Now Selling New Homesites|Now Selling Phase II|new section of homesites now open|NEW Phase Available Mid Winter|Final Homes Now Selling|Now selling in the Final Phase|Now selling in the newest phase|FINAL TWO HOMES NOW SELLING|now selling|Phase \\dA now selling|Phase II Now Selling|Pool sized homesites available|Retreat Home Sites Now Available|Phase 10 now selling|Phase 6 Now Selling|Now Selling Phase 14|NOW SELLING PHASE 7A|NOW SELLING PHASE III|Phase II Homesites Now Available|oversized homesites now available|Homes now available Phase IIB|NOW SELLING FINAL PHASE|Now Selling Final Phase|Phase I homesites now available|Phase I Now Selling|Now Selling Phase 2B|Now Selling Phase 2|FINAL 5 NEW HOMES NOW AVAILABLE|NOW SELLING Phase 2|NEW SECTIONS NOW SELLING|Final 5 Homes Now Available|Now Selling Phase II|PHASE 2 NOW AVAILABLE|PHASE 4 NOW AVAILABLE|final homes now selling|Now Selling New Phase 2|waterfront lots now open|Wooded Views Now Available|Now Selling Final Section|Final Phase Selling Now|PHASE 6 NOW AVAILABLE|Section 3 Now Open|New Section NOW SELLING|Phase Two Now Selling|NEW BUILDING NOW AVAILABLE|Newest phase now open|Now selling lots in Phase 2|Now Selling New Townhome|New Townhomes Available|New home sites are now available|Final home now available|New Home\\s*[S|s]ite[s]* Now Available|Now selling newly released home sites|Half-Acre Homes Now Available|now available Phase I|MORE OPPORTUNITIES NOW AVAILABLE|Immediate Occupancy Now Available|FINAL HOME NOW SELLING|Newest phase now open|Now Selling Phase Five|Now Selling Phase 3 Homesites|Now selling Phase 1 and 2|Now Selling PHASE III|Phase II Lots Now Open|Section II Now Open|New Phase Available Now|Cul-de-sac and Oversized homesites available|oversized homesites available |PHASE 9 NOW OPEN|NOW SELLING PHASE 5|NEW PHASE \\d+ NOW OPEN|NOW OPEN and selling fast|new phase Now Open And Selling|now open and selling|New Floorplans Now Selling|Phase 2 New Home Sites & Plans Now Available|New Townhomes Now Selling|WATERFRONT LOTS NOW SELLING|final homesite now selling|Final Section Now Available|New Homesites Now Selling|Now Selling Phase 12|FINAL HOMES SELLING NOW|Now Open & Selling|wooded homesites now available|PHASE 3 NOW SELLING|Now selling phase 3|Now Selling Building 5|Phase III Now Available|Now selling final homesites|waterfront homes now available|New Wooded Home Sites Now Available|Water view home sites now available|FINAL Phase Now Available|Phase 2 Home Sites Now Available|Phase 2 Home Site Release|New Home Site Released|Phase \\d Now Selling|Now selling Section Two|Phase III Homesites NOW AVAILABLE|LAST SECTION OF HOMESITES NOW AVAILABLE|Large Homesites Now Available|PREMIUM HOMESITES NOW SELLING|Final Release now available|Premium Homesites Now Available|pond and lakefront homesites available|Lakefront homesites now available|Now Selling Phase 6|final section now open|Now Selling Phase 10| now selling phase 4|FINAL HOMES NOW SELLING|Final Two Homes Now Selling|Over-sized home-sites available|Lake view and basement homesites available now|Phase 2 OPEN NOW|now selling new phase |Basement homesites available|BASEMENT HOMESITES NOW AVAILABLE|Lakefront homesites available|Basement homesites now available|NOW SELLING PHASE 4|PHASE 3 NOW OPEN|FINAL HOME SELLING NOW|New Townhomes Now Available|LOTS NOW SELLING|TWO NEW PHASES NOW SELLING|Now Selling Section 4|homes now available |Only 2 Finale Home Sites Remain|Only Six Home Sites Remain|Now Selling Final Inventory Home|Phase 4 Now Selling|New phase now available|newest phase now available|Final Ready Homes now available|Phase IV and V now Open |PHASE II NOW OPEN|Section III Now Open |Phase 7 NOW OPEN|PHASE 5 NOW AVAILABLE|first phase now available|Now Selling New Phase| Selling New Phase|Phase V now selling|New Homes Now Selling|Phase III Now Selling|Now selling new homesites|New Phase Available|Now selling third and final phase|Now Selling Phase II|NOW SELLING PHASE 1 & 2|NOW SELLING PHASE 1|SELLING FINAL PHASE|New Phase Now Open|new phase now selling|Second Phase Now Selling|Phase II now available|Phase V Now Available|Phase IV Now Available|Phase IV now open|New Homes Now Selling|Phase IV now Open|New Phases Now Selling|Now selling Section Two|Now Selling Sections 2 &amp; 3|Now Selling Sections 2 & 3|Now Selling New Section|Final Section Now Open|New Plans now available|New Homes now available|New phase now selling|New Phase Now Selling|New Section[s]* Now Available|\\d*\\s*home sites now available|New homesites now available|homesites now available|First Section Now Selling|new section now open|New Home Sites Now Available|New Section Now Selling|New Selling New Section|now selling new phase|Selling new phase|Phase Two - NOW SELLING|Phase 3 Now Selling|Phase 2 Now Selling|Now Selling Phase 2|New Homesites Now Open|section 2 now open|Phase III Now Open|Phase III Lots Now Open|NOW SELLING PHASE TWO|Now Selling NEW SECTION|PHASE II NOW SELLING|Now Selling Phase Three|final phase now selling|final phase NOW SELLING|Final Phase Now Selling|Now Selling Final Phase|NOW SELLING FINAL PHASE|Now selling final phase|Now Selling Final Section|Now Selling New Homes|homesites now selling|Now Selling Phase I|Now Selling Phase IV|Now selling phases 4 and 5|Final Section Now Selling|New Phases Now Open|Now Selling New Section|Now Selling New Floorplans|now selling new phase|Final Phase Now Available|Final Homes now available|Final Home Sites Now Available|LAST HOME NOW AVAILABLE|New Homestes Now Available|now available|Quick Move In Now Available|New Homesites Now Available|NEW HOME SITES ARE NOW AVAILABLE|homesites now available|homesites now available|Final Homesites Now Available|PHASE ONE RELEASE NOW OPEN|New Homesites Now Open|Phase 2 now open|Phase 2 Now Open|Only a Few Lots Remaining|Only 1 waterfront homesite remains|Just a Few Remain|3 Homesites Released in Final Phase| PHASE 2 NEW SECTION NOW SELLING|PHASE 11 NOW OPEN| NEW SECTION NOW OPEN AND SELLING |NEW PHASE NOW OPEN|NEW SECTIONS NOW OPEN|New Section Now Open|Phase 6 is now open|Phase three now open|Phase Four NOW OPEN|NOW OPEN|Now Open|Phase 4 now open|Last Section Now Open|Second Phase Now Open|New phase homesites just opened|Section II Open|NOW OPEN|Final Homes Now Selling|Final Homes Now Selling|Final Phase Available|Opening New Phase|Opening April 2020|Opening February 2020|Opening Spring 2020|Opening Fall 2020|Opening Fall 2021|Phase Two Now Selling| Now Selling in New Phase|PHASE 3 HOMES NOW SELLING|NOW SELLING HOMESITES|FINAL \\d HOMES SELLING NOW|Now Selling|NOW SELLING|NEW PHASE SELLING NOW|SELLING NOW|Now Selling");
//			add("new phase homesites now available");
			add("Selling Lots Only");
			add("Just 1 Left|Limited Lake Homesites Remaining|Final Section Released|Lakeview Homes Available");
			add("new phase selling quickly|new phase selling quickly|final few homes are selling quickly|Homes are selling quickly|Phase I homesites selling quickly|Phase I selling quickly|Phase 1 lots are selling quickly|Lots selling quickly|Phase II Selling Quickly|Selling Quickly");
			add("56 Home-sites Available|only 52 sites available");
			add("Phase 3 Homes Selling");
			add("Pond Home Sites Available|New phase Pond home sites available");
			add("Final Home Site is Now Available ");
			add("Water View Homes Available");
			add("ELEVATOR TOWNHOMES COMING SOON");
			add("grand finale");
			add("One Lot Remains");
//			add("selling Summer 2022");
			add("71 home sites released in Phase 1");
			add("Now Selling in Final Section");
//			add("New Section Now Open");
//			add("Coming August 2022");
//			add("Opening Late Fall 2022");
		//	add("NEW SECTION NOW OPEN");
//			add("FINAL PHASE NOW AVAILABLE");
//			add("Coming 2nd Quarter 2022|Coming Soon 2nd Quarter 2022");

		}
	};

	public static HashMap<String, String> propTypes = new HashMap<String, String>() {

		{

			put("multi-generational situation|multi-generation living| multi-generational families|multi-generation suite|Multi-Generational Home|Multigenerational neighborhood|multi-generational family|multi-generational gathering space|Multi-generational and guest suite|multi-generational suite|Multi-Gen Suites|Multi-Generational Suites|multigenerational suite options| multi-generational bonus| Multi-Gen Living Space|and multi-generational|Optional Multi-Gen Suite|Multi-gen suite|multi-gen casitas|MultiGen Innovation|multigenerational living|multi-generational living",
					"Multi-Gen Homes");
			put("executive-style|executive luxury|Executive Apartments|executive style|executive style townhomes|executive home sites|executive estate homes|Executive home office |Executive Single Family homes|Executive Single-Family Home|Executive-level living|executive style home|Executive Home Collection|Executive Collection|Executive Series|Series:</span> Executive|executive-style homes|Executive, Single Family Homes|Executive homes|Executive homes",
					"Executive Style Homes");
			put("flex space|Flex Series|FLEX Homes|Flex Room", "Flex Homes");
			put("farmhouse|farmhouse-style architecture|farmhouse inspired home|>Farmhouse<|Prairie, Farmhouse|farmhouse inspired townhomes|Farmhouse Style|Meadowbrook Farm's single family homes|Farmhouse-style exteriors|Farmhouse architectural styles|Farmhouse home|farmhouse and craftsman|Craftsman and Farmhouse|craftsman or farmhouse-style|Farmhouse-inspired architectura|Farmhouse Home Designs| Coastal Farmhouse Series|award-winning Farmhouse|Coastal Farmhouse-inspired|Farmhouse architectural styles|red brick farmhouse| luxury Farmhouse-style|farmhouse and prairie elevations|farmhouse interpretive townhomes|Farmhouse</h4>|Farms Villas|modern farmhouse|The Farmhouse|Farmhouse exterior|Farm House, Craftsman|Farmhouse architecture|Farmhouse-style residences|Modern Farmhouse style|farm\\s*house style|Farmhouse styles homes|farmhouse designs|designed Farmhouse|Farmhouses designed|Farmhouse and Ranch|Farmhouse and Ranch|farmhouse elements|Farmhouse, Craftsman|Cottage, Farmhouse|Farmhouse and Traditional|Farmhouse Style Architecture|farmhouse inspired homes|Farmhouse-style Homes",
					"Farmhouse Style Homes");
			put("Robinson Manor|River Oaks - The Manor|Arcadia Manor|RYANWOOD MANOR|Southlake Manors|Manor[s]* at | - The Manors|The Manors|manor house|Manors Collection| manor style homes|Manor Collection|The Manors home|Manor Collection of homes|Manor - Estate home|MANOR SERIES|Manor Homesites|Manor Estates|Manor Series homes|Windsor Manor home|beautiful Manor home|Manor Home Models|Manors Homes at |Manor Homes</a>|The Manor Collection|Single Family Manor Home|Manor Homes New Home Community|Castleton Manor Community|Manor</h3>|Estate homes, Manor homes|Manor ranch designed homes|Manor Homes | Manors Homes",
					"Manor Homes");
			put("Loft|with Loft |Loft\r\n\\s+</li>|loft/game room|spacious loft| a loft|OPTIONAL LOFT|Loft makes|with loft options|second story loft|and a loft|a loft and |bright loft|roomy loft|available loft| plus loft|Activity Loft|multipurpose loft|Bedroom Loft|Lofts Available|Loft or Optional Bedroom|a study loft|lofts, dens|sprawling loft|functional loft|lofts or flex rooms|Library Loft|lofts, decks|bedrooms \\+ loft|Loft</li>| Lofts in |<li>Loft[\\*]*</li>|Loft Available|den & loft|a loft as|and loft|the loft|lofts and|house loft|<li>Loft[s]*</li>|Loft</li>|at Loft|loft plus|loft space|huge lof|loft-like design|loft options available|Loft in|Lofts At|The Lofts|recreational loft|large Loft| Loft</div>|, Loft,|patios. Lofts|Loft</span>|or Loft|City Lofts|loft with|Private Family Loft|<li>\\s*Loft\\s*</li>|entries, loft|, loft[s]*,|flexible loft|loft and|Loft and Bonus Room|loft, and two-car garage|expansive loft|loft-style units|open loft|Lofts At Uptown|lofts and rooftop|spacious loft|upper-level loft|Loft Designs|Loft and Terrace| lofts, and more|Loft and single-story flats|two-story lofts |2-story loft|Loft Apartment|Loft Row|loft-style apartments|Lofts at Village Walk|loft area|</span>Loft</span>| opt. loft home|Loft Home| Lofts is a|second-story loft|Upstairs [l|L]oft|loft upstairs|upstairs loft|loft, and balcony|lofts provide|Loft or Opt. Bedroom|of the Loft| and loft|Floor Loft|Floor Loft|Floor with Loft|Optional Loft",
					"Loft");
			put("custom lots in Lincoln|custom architecture|custom-built home|custom-quality features|custom home|custom build|Custom Plan|custom infill homes|Single family Custom Homes|home the custom feel|custom-built look|exceptional custom home| the custom home|this custom home|custom home nestled|custom coastal residence| elegant custom home |our custom designed plans|Custom Single Family Home| Custom-feel ranch|interiors custom designed|Grand Estates Custom|custom luxury homes|semi-custom homes|custom home of their own|Custom Home Builders Albuquerque|custom homes|custom-designed|custom lakefront estate|Luxurious Custom Homes|each custom home |Oaks Custom Homes|custom dream home| traditional, custom home|custom estate home |Custom-feel Prestige homes|custom homes are|custom apartment |custom plan|truly custom homes|custom architectural|kind custom home|Custom Finishes|custom home appeal|PRESTIGIOUS CUSTOM HOMES|Custom-style living|Summit Custom Homes|James Engle Custom Homes|custom home design|custom-designed townhome|custom home sits|Beautiful custom home|stunning custom home|Custom Home Communities|luxury custom homes|single-family custom homes|customizable homes|Custom Homes in |Designer Custom Finishes|semi-custom homes|customizable designs including|Custom homes plans|semi-custom designer homes|custom-feel all-ranch homes|custom single-family homes|appointed custom homes|unique, custom, luxurious|unique custom homes|custom 2-story plan|custom designed new homes|custom design options |custom-designed lower|great custom home |& Custom Homesites| and custom homes|custom home features |your custom new home|Custom Luxury Homes Available|finest custom homes|in custom homes | custom home designs|Keystone Custom Homes| a custom home|magnificent custom homes|Custom Coastal Cottage-Style homes|new custom home",
					"Custom Home");
			put("craftsmanship|Craftsman|craftsmanship|Craftsman Style|Craftsman floor plan|>Craftsman<|craftsman-style duets|Craftsman style details|Western Craftsman plan|craftsman or farmhouse|Stunning craftsman exterior|Crafted Luxury|Craftsman and Farmhouse|Craftsman Series Floor Plans|California Craftsman|Craftsman Floor Plans|gorgeous Craftsman style|Craftsman style ranch |Craftsman inspired details|incredible Craftsman style finishes |classic craftsman and colonial style |Craftsman Architectural Detailing|Adorable Craftsman style|fantastic craftsman style|These Craftsman style floor plans |classic craftsman design|craftsman style plans|Hartford Craftsman|craftman-style homes|and Craftsman|craftsman style with extensive|craftsman style trim|Craftsman</h3>|Craftsman</h4>|Craftsman, Prarie|Craftsman-style home|Spruce Craftsman|Craftsman Cottage style|Modern Craftsman|Craftsman,Traditional|craftsman styling|Craftsman-style architecture|Craftsman and Traditional|Craftsman Plus floor plans|Craftsman, Traditional|Poplar Craftsman|craftsmen style homes|Craftsman\\s*</div>|Craftsman style brand new homes|Farmhouse, Craftsman|Farm House, Craftsman|Craftsman-style home|Craftsman, and Farmhouse|craftsman-styled homes|craftsman details |craftsman style patio|Craftsman charm |Craftsman Homes|craftsman home|Craftsman features|Craftsman-style exterior|craftsman style facade|Craftsman Colonial|standard Craftsman style|spacious Craftsman style|New Craftsman, Farmhouse|Craftsman Bungalow|craftsman-style exterior designs|crafted custom designed flat|Craftsman exterior styling|gorgeous Craftsman Exterior|Beautiful Craftsman Home|Craftsman style new homes|Craftsman Style swim community|Craftsman and European-style homes|community of Craftsman| detailed Craftsman-|craftsman tradition|Craftsman style low maintenance homes|Craftsman townhomes|Craftsman New Homes|Craftsman influenced exteriors |Craftsman Style Home|Craftsman style bungalows|Craftsman cottages|Craftsman exteriors|Craftsman architecture| craftsman style two story home|Craftsman architectural styles|offers craftsman style|craftsman-style plan|Coast Design/Craftsmen Style|craftsman-style townhomes|Craftsman influenced architectural| fine craftsman details|Craftsman style homes|Craftsman-style home|Craftsman-style exteriors|craftsman-style design|Sierra Craftsman|craftsman-style single family|Craftsman-style single-family|Craftsman architectural styling|Craftsman Plus Collection| craftsman-style homes|craftsman style architecture| well-crafted homes|Craftsman, coupled|Craftsman-inspired|craftsman style crown|This Craftsman styled| craftsman style trim accent|exquisite craftsman style|with craftsman exteriors|Craftsman-style Sweetwater home|Craftsman style exterior|Craftsman Style Homes|and craftsman homes",
					"Craftsman Style Homes"); // Craftsman
			put("estate homesites|Lochridge - The Estates|The Estates Of Columbia Ridge|The Preserve - The Estates|Rose Hill Estates|Clear View Estates|WESTLAWN ESTATES|SERENITY ESTATES|QUARRY RIDGE ESTATES|Hidden Lakes Estates|Heron's Bay Estates|Northlake Estates|Woodbridge Estates|Tarragon Estates|Stone Mill Estates|Donwood Estates Singles|Saddleback Estates|Montalcino Estates|The Estates, and The Enclave sections|Everly Estates|Patriot Estates|Maefield Estates|Estate style|estate community|ESTATES COMMUNITY|Estate Residences|Island Lake Estates|Winnetka Estate|Wellsprings Estates | estate home|The Estate section|estate-style designs |estate lots|custom lakefront estate|Estates architecture|Grand Estates Custom|Lakeside Estates|Estate Style Living|traditional and estates|estate size home sites|Manor - Estate home|- Estate home|Estates at Mill Creek|the estate home|Estate Series|single-family estate properties|estate-sized lots|Single-Level Estate Home|Estate Collection|estate floorplans|new estate home|estate-caliber homes|large estate home|estate-sized home|Estates features|Estates Series community|estate style community|Estate Size Homesites|estate sized lots|estate-sized home sites|Estate Home Community|Estates Series Home|Estate & Signature Collections|luxurious estate home|luxury living estate living|Estates sized homes| estate-style living |estate style living |luxurious estate living|Estate-Sized Homes|Estate-size home|estate home featuring| Estate Series |Estate style crown |estate style lots|estate sized home sites|Estate style homes|estate-sized homes sites|sprawling estate home|The Estate Collection|Estate Homes|to estate homes|estate-like homes|Estate homes, Manor homes|single family and estate homes|Estates Custom homes|Estate-sized homesites| Estates home| estate homes |single-family estate homes|luxury estate homes |estate homesites|luxurious estate homes|Estate homes New Home|Single Family Estate Home| elegant estate residences|estate home communities| estate-style home|estate homes feature|estate series homes|estate homes situated |Estate-Style Homes",
					"Estate-Style Homes");
			put("Two-Family home|2-Family Home|2-Family Home", "Two Family Homes");
			put("detached villa|detached carefree villas|detached residences|detached apartment|detached row homes|detached homes|detached condos|detached 2 and 3-story|detached luxury single-family residences|detached three-story|detached villas|detached floorplans|Residential-Detached|detached bungalows|Detached<br>Homes|Single-Family Detached|detached single-family|detached, single-family|Detached Condo|Single Family Detached|Detached and paired|Detached Single|dream home – detached|detached two-story homes|detached condominium|detached, two-story|Detached Home|detached homes|Detached Homes|detached townhomes|detached ranch|detached home|Detached Town Homes",
					"Detached Home");
			put("single and multifamily homes|<h2>Single Family House|Single-Family Custom Homes|Single-Family Homes|<td>Single Family Home</td>| single and multi- family homes|single family home| single- and multi-family|single-family homes|Single Family Homes</li>|Single-Family Residence|Single Family Homes|Single luxury family|single-family|Single-Family|SINGLE FAMILY|single family|single and multi-family|Single-Family Home |Single Family Homes",
					"Single Family");
			put("apartments|apartment", "Apartment Homes");
			put("traditional-inspired|Collection: Traditional|traditionally|traditionally style|Traditional Style Homes|>Traditional<|Traditional exterior|traditional 2|traditional as well as trendy|Traditional Meets|traditional Southern California townhome|Traditional Tuscan|family in your transitional|Southern traditional| traditional floor plan |Traditional-Style Condominiums|Traditional Colonia| traditional, custom home|traditional inspired exteriors|traditional master-planned community|- Traditional Series|Poplar Traditional|traditional three and four-story|traditional-style family homes|traditional layout|Traditional, Modern, or any|traditional one-story|a traditional homes|Traditional three-story|traditional townhome|New Tradition Homes|traditional family homes|Traditional</h5>|traditional single |TRADITIONAL NEW HOME|traditional-style brick homes|Traditional</h3>|Traditional, Low County|Traditions floor plan|traditional architecture|Traditional-style homes|craftsman tradition|traditional two-story style home|traditional features|Traditional, Bungalow|Craftsman,Traditional|traditional upper level|traditional home arrangement|Traditional & Craftsman style exteriors|Windsor Traditional|traditional floor plans|traditional single family|Traditional Townhomes|traditional ranch-style homes|Traditional architectural style|Traditional Ranch homes|traditionally styled homes|Traditional single family homes|traditional collection| traditional communities|Traditional Singles|traditional-series|traditional three story|traditionally-styled|features Traditional|Traditional, and|Traditional and|Traditional</li>|Traditional Family Neighborhood|Traditional Series|<p>Traditional,|Traditional two story home|traditional home|traditional design|traditional elegance|traditional new homes|traditional homes|traditional neighborhood|Traditional Community|traditional single-family|Traditional Neighborhood Homes|mix of traditional|traditional style and|Cottage, Traditiona|Traditional, Cottage| traditional style|traditional one- and two-story homes|Traditional</div>| traditional two-story|Traditional Texas-style homes|Traditional and Cottage Collection|Traditional and Italian",
					"Traditional Homes");
			put("New Luxury|Luxury Attached Homes|luxurious|coastal luxury|luxury residential|luxury 2|touch of luxury|luxury meets|feature unique luxury|spacious luxury feel|as well as luxurious|Luxury Redefined|Rustic Luxury|luxurious style|luxurious resort-style pools|Luxury Series|luxury resides|luxury patio home|luxury in this well-designed home|stunning luxury appointments|luxury lifestyle|Luxurious, estate-style|Luxurious Single-Level| experience of luxury| luxury, gated community |luxury amentities|luxurious new townhomes|with luxurious details|all the luxuries and amenities|luxury made livable|style luxury of|luxurious lifestyle|any luxury home|luxury options|luxurious single-family|luxurious plans|LUXURY GARDEN HOMES|luxury of personalizing|luxurious interior|luxury of home|luxurious main level|[l|L]uxurious [O|o]wner's [S|s]uite|Luxurious Interior|custom luxury|luxury master-planned|luxurious community|Luxury features|luxury design|luxurious standard features|luxury owner retreats|Luxuriously Appointed Interiors|Luxury elevator townhomes|luxurious home|luxurious collection|luxurious new Coach Home|luxury, single family|luxurious features|Luxurious Living Spaces|luxurious dream home|luxurious finishes|luxury designed homes|luxurious condominium|luxury master suites|luxuriously appointed homes|luxurious\\s+single family homes|features luxurious|luxurious maintenance|luxury owner’s suite|LUXURIOUS MASTER SUITE|luxurious amenity|luxurious townhome|luxurious options|luxurious and|LUXURY MID-RISE CONDOS|Luxurious Master Suite|luxurious Owner’s Suite|luxurious resort-style amenities|luxury of living|luxurious multi levels|luxurious owner’s suite|luxurious carriage home|luxurious single-family homes|luxurious finishes|luxury residences |Luxurious Included |luxury and fresh|luxurious owner's suites|Luxury Included|luxury and conveniences |luxurious amenities| luxury finishes| luxurious single level |luxurious master suites|luxurious estate living|new home luxury|luxury townhomes|Stylish luxury meets|warmth, luxury|Luxury Gated Community| luxury flats|Luxury Cottages|Luxury Custom Home|Luxurious new homes|luxury for buyers|luxury villa |many luxury features|luxury-inspired amenities|luxury town home|Luxurious sized Master Suites|luxury style|luxury amenities|luxury real estate community|Luxury Penthouse|luxury ranch-style homes|luxury new home|luxury rental residence|lifestyle and luxury|Luxurious Homes|luxury gated townhome|luxury gated|Luxury Retirement Community|luxury condo|luxury single| luxury living.|luxury neighborhood|new luxury plan|luxury collection|luxurious living|luxury townhomes|luxury life|AFFORDABLE LUXURY|luxury floorplans|luxurious lakeside community|luxury homes|luxury home|luxury patio homes|Luxury Estate Homes|homes that combine luxury|luxury gated communit|Luxury Single-Family Homes|Luxury Single Family Homes|luxury one-| luxury of 37 gated| offer luxury|luxury ranch homes|luxury community|luxury apartment|luxury detached|Luxury, garage townhomes|luxury affordable|Luxury Living|luxury townhome|Luxury Villas|luxury single|luxury paired homes",
					"Luxury Homes");
			put("PENTANGELI ROW|Rowhomes|Row House Collection|row homes", "Row Homes");
			put("Multifamily|MULTI FAMILY|multi\\s*-?\\s*family,|Multifamily condominiums| Multi-Family</li>| multi\\s*-?\\s*family |>Multi-Family |Multi family&|<li>Multi-Family</li>",
					"Multi-Family");
			put("condo(minium)?|CONDOS|condominium homes", "Condominium");
			put("Villas|<h1>Villas</h1|Paired Villas|provided luxury villas|detached carefree villas|The Villas|Condominium Suites, Villas|ranch Villa|Branch Villas|single family homes, villas,|and villas|attached villas|Villa</em>| Villas</th|Twin Villas|courtyard villa|villas |luxurious villa|/Villa |Villa Homes|Villas | Villa,| villa |villa-style homes|Villa</span>",
					"Villas");
			put("Town Home Sites|stunning Town Home design| Town Home plans|townhome[s]*|<em>Triplex Townhomes|<em>Townhomes| Town Homes| - Townhomes|multi-level townhomes|multilevel townhomes|Luxury Townhomes|2-level townhome| townhomes,| Townhome | townhomes |and townhomes |townhome style| Townhomes</b>|Townhome/Condo |Townhome Condo |and townhomes.|>Townhomes<",
					"Townhome");
			put("single-family home|Single-Family|SINGLE FAMILY", "Single Family");
			put("COTTAGES", "Cottage");
			put("<h2>HOA</h2>|<h3>HOA</h3>|Low HOAs|included in HOA|Farms HOA|Information Desc HOA|provided by an HOA|by Homeowner Association| HOA-maintained |Active Homeowner Association|HOMEOWNER’S ASSOCIATION|low HOA fees|HOA-maintained front yards|with HOA|Residential HOA| HOA Dues|low fee HOA|Homeowner&rsquo;s Association |HOMEOWNER ASSOCIATION DUES|Homeowners' Association|resident Homeowner Association|<strong>HOA|Place HOA|Low HOA fees|Low HOA|Home Owner’s Association|HOA</a></li>|by HOA| HOA | HOA |hoa |HOA Info|HOA dues|hoa dues|home owners association|Homeowners Association|HOA <span|Homeowner's Association |Homeowner's Association|Home Owners' Association|The HOA|<li>HOA|HOA</li>",
					"Homeowner Association");
			put("Covered Patio|Patio Homes Collection|Patio-style home|your patio|Patio Home| Extended Covered Patio|Covered Patio|patio|Patio|covered back patio",
					"Patio Homes");
			put("homes paired|Paired Townhome|paired home|Paired Home Series|paired courtyard homes|paired patio|paired home villas|paired-patio home|Paired and single-family home|paired residences|paired villa |paired new homes|Paired Homes|paired patio homes| Paired Ranches|Paired Villa homes|paired villas|paired and single family",
					"Paired Homes");
			put("twinhomes|twin \\(attached\\) house|Twinhome Floor Plans|saddlecreek-twinhomes|twin-style carriage homes|Twin Homes|Twin Villas|Twin Villa|Twin Home",
					"Twin Homes");
			// put("Zero-Lot-Line Home", "Zero-Lot-Line Home");
			put("Cottage Architectural|Cottage", "Cottage");
			// put("Cabin ", "Cabin");
			put(" Mediterranean |Mediterranean-inspired|featuring Mediterranean|feature Mediterranean|Mediterranean Exterior|Mediterranean architecture|Mediterranean Series|include Mediterranean|Contemporary Mediterranean Style|Mediterranean and Tuscan designs|Mediterranean Revival",
					"Mediterranean Style Homes");
			put("Front courtyard|courtyard garden| court-yard style homes|Courtyard|courtyard", "Courtyard Home");
			put("Multi-Family Housing Products", "Multi-Family Housing Products");
			put("<strong>Duplexes|Duplex | Duplex|Duplex</a>|Duplex", "Duplex");
			put(" triplexes|Triplex |3-Plex|Tri-plex|tri-plex", "Triplex");
			// put("4-Plex|4 Plex","Fourplex");
			put("5-Plex|5 Plex", "Fiveplex");
			put("6-Plex|6 Plex", "Sixplex");
			put("7-Plex|7 Plex", "Sevenplex");
			put("fourplex|Quadraplex|4-Plex|4 Plex", "Quadraplex");
			put("commons area |common&nbsp;area|common area|Common Areas", "Common Area");
			put("WINDSOR GARDENS|yoga garden|Apartments\\s*,\\s*Garden Style|courtyard garden|garden style community|Garden-Style Apartment|garden style apartments| garden-style designs| garden-style|Gardens Collection|garden-style apartment buildings|garden style condominium|garden style floor plans|Garden-style apartment homes|Garden –Style Apartment Homes|Garden Collection|garden-style buildings|garden-style apartments|garden style apartment |garden style homes|garden-style community |garden apartments|Garden Series|garden home|garden/patio home|Garden View Home|garden style buildings|garden courtyard|garden patios|garden patio|Garden Villas|Garden Homes",
					"Garden Home");
			put("Apartment Flat", "Apartment Flat");
			put("Carriage-Style Townhomes |beautiful carriage|Carriage and Cottage Collections|The Carriage Collection|carriage house|Carriage</span>|Carriage Home|carriage flats|Carriage Style|Carriage, Townhom|carriage-style homes|Carriages Collection",
					"Carriage Home");
			put("Coach Home", "Coach Home");
			// put("townhouses|Townhouse", "Townhouse");
			put("Condo-Hotel Residences", "Condo-Hotel Residences");
			put("Private Resorts", "Private Resort");
			put("Hotel Residences", "Hotel Residences");
			put("Private Residence Club (PRC)", "Private Residence Club (PRC)");
			put("Housing Cooperative (Co-Op)", "Housing Cooperative (Co-Op)");
			put("Interval Ownership", "Interval Ownership");
			put("Destination Clubs", "Destination Clubs");
			put("Land Lease", "Land Lease");
			put("Common-Interest Developments (CIDs)", "Common-Interest Developments (CIDs)");
			put("Multi Gen Home|multi-generational situation|multi-generation living| multi-generational families|multi-generation suite|Multi-Generational Home|Multigenerational neighborhood|multi-generational family|multi-generational gathering space|Multi-generational and guest suite|multi-generational suite|Multi-Gen Suites|Multi-Generational Suites|multigenerational suite options| multi-generational bonus| Multi-Gen Living Space|and multi-generational|Optional Multi-Gen Suite|Multi-gen suite|multi-gen casitas|MultiGen Innovation|multigenerational living|multi-generational living",
					"Multi-Gen Homes");
			put("Covenants, Conditions and Restrictions (CCRCs)", "Covenants, Conditions and Restrictions (CCRCs)");
			put("Bungalows Homes|Bungalow Series|Winnetka Bungalows|bungalow style homes|bungalow-inspired townhome |Bungalow inspired |bungalow-style beach home|detached bungalow|BUNGALOWS AT BANNING LEWIS |styles like Bungalow,|Bungalow</p>|stunning Bungalow|The Bungalows",
					"Bungalow Homes");
			put("Modern Coastal Classic|Coastal Style|coastal neighborhood|Coastal architecture|coastal and active lifestyle|coastal-style homes|Coastal Classic plan|custom coastal residence|Coastal-themed community|beautiful coastal area home|Marlene Coastal|Coastal cottage|Coastal Tuscan| coastal lifestyle|coastal contemporary architecture|coastal villas| Coastal Farmhouse Series|coastal community|A Coastal Inspired|coastal-inspired|coastal cottage-style homes| coastal living| coastal homes|Coastal Farmhouse-inspired|coastal craftsman|COASTAL COLLECTION|coastal communities|COASTAL LIVING|coastal design",
					"Coastal Style Homes");
		}
	};
	private static HashMap<String, String> commTypes = new HashMap<String, String>() {

		{

			put("single\\s*-?\\s*family", "Single Family");
			put("multi\\s*-?\\s*family", "Multi-Family Dwellings");
			put("town\\s*-?\\s*homes", "Townhomes");
			put("tower and estate residences", "Tower & Estate Residences");
			put(" condo(minium)? ", "Condominium");
			put("55_plus", "55_plus");
			put("carriage homes", "Carriage Homes");

		}
	};
	private static HashMap<String, String> dcommTypes = new HashMap<String, String>() {

		{
			put("1 Story|first-floor design|single-story|first and second floor owner|single or multi-story homes|one- and two-story single-family homes|story 1|first- or second-floor|beautiful single-story homes |1 Story</li>|Floor: 1|One- and two-story|single- and two-story|1 Stories|single and two-story|1 Story\\s*</p>| Stories 1|Stories: 1|1 Story\\s*</p>|1 Stories|single-level home|1-and 2-story|1 Story</h5>|one or two-story homes|<em>1-Story</em>|Story 1.00|Stories: 1.0 |single and two story homes|1 - 2 Stories|1 - 1.5 Stories| first-floor living |1 or 2-story home|1-Story</li>|single floor living|1st and 2nd floors|with first floor|Offering first floor|first floor owner|First Floor|first floor living|single-floor| 1st floor|one or two-stories|1- or 2-story|One-Level|one level|one level living|first story|Stories\\s*\\n*\\s+1|One Level Living|one level living |1, 1.5 and 2-story|Story: 1.00|one-level living |1- and 2-story|one- & two-story|one-level homes|Single-Level|1 Story|one & two-story|one-and two-story|one and two story|1 story|\\s*Stories 1|single level|single story|1  Story|Single- and 2-Story|1 &amp; 2 story|Stories: 1 |1 or 2 story| 1- &amp; 2-story|1- & 2-story|1 and 2-Story|single-level and two story|single-level and two story|one and two story|One &amp; two story|Story 1<|Single story|1 and 2 story|ingle &amp; two-story|Stories: 1<|one and two-story|1 & 2 story|One- and two-story|One- and two- story|one and two story|One or Two Story|1-3 Stories|one and two story|1-Stories|single-story|1 & 2-Story|single and two-story|1-2 stories|One and two-story|Stories 1|one-story|single story|1 Story Home|1  Story Home| 1-story|One Story| 1-story|single-story|1 Stories| 1 STORY|Stories:</span> 1|1�story|single- and two-story|one story| one story|One, one and half and two story|1.0 Story",
					"1 Story");
			put("reverse story-and-a-half homes|Reverse-one-half-Story|reverse one and a half story|Reverse 1.5 Stories|Stories: Reverse 1.5|Reverse 1.5 Story|REVERSE 1.5 STORY",
					"Reverse 1.5 Story");
			put("1.5-story|1.5 &amp; 2-story|1.5 and 2-story|1 1/2 story condos|traditional 1.5 story|1½-story|1.5 Story</li>|1.5 Story|Stories 1.5| 1.5 Story<|1.5 - 2 Stories|1 1/2 story home|1.5 story home|1.5 Story plan|One-and-a-half story|Stories: 1.5 &nbsp|One and a half-story|1, 1.5 and 2-story|Stories: 1.5|1 1/2 Story|1.5-story|1.5 Stories|1-1/2- story|Story 1 1/2|1.5 story|one &amp; a half story|one & a half story|story-and-a-half|one-half story|1 ½ story|1 &frac12; story|one-and-a-half or two story|1-½ and 2-story|story and a half|1.5 Story |Story 1.5|Stories: 1.5|Stories: 1.5|story 1 - 1.5|1.5 and 2 story|1/2 story|stories\" value=\"1.5|one and a half story",
					"1.5 Story");

			put("2-Story| 2nd story | stunning two_story|two- and three-story|2 Story</li>|2 Story</span>|2-story homes|Floor: 2|2 stories |2 story</td>|2 Story\\s*<br/>|2 STORY</div>|2-story|2 Stories| Stories 2|</span>2 Story</span>|2 Story\\s*</p|<strong>2</strong> Stories|<p>2-Story|Two-level townhomes|2 Story,|2nd Story|two-story home[s]*|<em>2-Story</em>|two-story homes|\\s+2-Story |2-3 Story | two level townhome | 2-story design |2 Level Traditional-Style|2-3 story homes |2- and 3-story |two-story floor|two-story home[s]*|2&nbsp;&amp; 3-story|2-Stories|second floor owner|second-floor|Second Floor| 2 story| 2 story |2 - 3 Stories|two-story|2- or 3-story |2 and 3 story| 2nd floor |2 Story</strong>|2-level condo |two-stories|second level |2-level luxury|second level|two-level homes|two and three-story| 2-Story|2 &amp; 3 story|<li>2 Stories|two to three stories|2.0 story|2-level condominiums| 2-Story|second-story|Story: 2.00|2 and 3 stories|2-level condos|Story 2|one and two story| 2 story|even\">Story 2|\\s*Stories 2|Stories: 2|two story| 2 Story|Stories:</span>2|2-Stories|Two stories|two-story|two and three story|two story |Stories 2|two- story|Second Story|two-story|Story 2.0|2  Story Home|2 stories| 2 story| 2 STORY|two-Story|[T|t]*wo-[S|s]*tory| Two-story| 2-Story|two story|Two Story| 2-story|2 Story\\s*|TWO STORY|Stories:</span> 2| 2�story|two- and three-story|Stories: 2|two- to three-story",
					"2 Story");

			put("2 1/2 story|2 1/2 Story|2.5 stories| Story 2.5|2.5 Story", "2.5 Story");
			put("three-story|three story|Three-to-Four story|three and four-story|Tri-level terrace|two- and three-story| three to four stories|third level|third-floor loft|the third-floor|Floor: 3|3-story designs|3-Level Floorplans| three story |tri-level home|three- to four- story|Series:</span> Tri Level|3 Stories|story 3| third-story |3 Stories|three-level Carriage-Style Townhomes|3-story townhomes|three-level| 3.0 story|3 Finished Levels,|3-Level Luxury |3 Level,|3 levels of living|3- and 4-story|3-level townhome|3 and 4 level townhomes|3rd level|3 and 4 level|3 Stories|third story|3-4 story|3 and 4 story|3-level townhomes|three stories|Third floor|3 story|Stories 3|Three-Story|Story 3 |Stories: 3|Stories 3|two- to three-story|three to four-story|3-Stories|three story|three-story|3 Story|story 3.0 |3-story townhomes|3-story|Three-story| 3 Stories|Stories:</span> 3|3 Story|three-story|3�story|Stories: 3|3 Story",
					"3 Story");
			put("Stories: 3.5|3.5 Stories", "3.5 Story");
			put("four-story|four levels |4th floor|4 Stories|fourth floor|three and four-story| Floor: 4|4-level home|4 Levels|fully finished four-level|four- story|Four story |four stories|4-level townhomes|4 finished levels|4th level bonus suites|4th level bonus suites| four level townhomes |4 level townhomes|3-4 Stories|Stories:</span>4|4-story|four-story|Stories: 4|4 Level,|4 level|4 Stories|4 story|Story 4|Stories 4|4�Story",
					"4 Story");
			put(" five-story|five-level designs|five-level floorplans|Floor: 5| 5-story| 5-level design| 5-level plans| 5th-floor |Stories:5| 5 Story|five levels| 5-story|5 Story mid",
					"5 Story");
			put("Split floor plan| split floorplan|split bedroom home| split level|Single Level Split Plan|split plan design|split and rambler home|Series:</span> Split Level|split floor-plan|2-Story Bi-Level|Split Level</span>|Split-level home| a split level|Split-Level|Bi-Level|split plan",
					"Split Level");
			put("five-level splits", "Five-Level Splits");

			put(" multilevel townhomes|Multi-Level|multi-level|Multi Level|multi-story", "Multi-Level");
			put(" Ranch |Ranch & Two-Story|ranch style|Ranch & 2-Story Plans|<li>Ranch floor plan|Rancher Series| Rambler|Rambler</p>|ranch floor plan|ranch and two-story|1 ranch|Ranch & Smart|Ranch Plan|Ranch Style Design|Ranch floorplans|ranch-style floor plan|Ranch Elevation| Ranch architectural styles|Ranch and |Spacious ranch plans|all-ranch plan community|listing\">\\s+Ranch|Ranch Patio |ranch-style|ranch home| Ranch | ranch| ranch-style |Ranch</span>| Ranches | ranch,| Ranch",
					"Ranch");
			put(" nine-level |Nine stories|9th-Floor |nine-story|, 9 floors|9 story", "9 Story");
			put(" 6 story|six-story| 6-story ", "6 Story");
			// put("five-story|5 stories","5 Story");
			put("seven-story|, 7 floors|7 Story", "7 Story");
			put("eight-story|8 Story", "8 Story");
			put(" ten-story | 10- and 12-story|a 10-story| 10-story ", " 10 Story");
			put(" 12 stories| 12-story| 12 story|tweleve story|story 12", "12 Story");
			put("13 stories|13-story|13 story|thirteen Story", "13 Story");
			put(" 11-story|11th-floor", "11 Story");
			put(" 14-story|Fourteen-Story", " 14 Story");
			put("top 15 floors| 15-story ", "15 Story");
			put(" 16 story ", "16 Story");
			put("17-story|17-Story", "17 Story");
			put("nineteen story | 19-story", "19 Story");
			put("18-story", "18 Story");
			put(" 20-story,", " 20 Story");
			put(" 21-story", "21 Story");
			put("22 Story| 22-story ", "22 Story");
			put("25th-floor ", "25 Story");
			put("25 Stories|25-story", "25 Story");
			put(" 26 story ", " 26 Story");
			put("30 floors", "30 Story");
			put("32-story", "32 Story");
			put("31 story", "31 Story");
			put("33 stories ", "33 Story");
			put("40-story ", "40 Story");
			put("41 stories", "41 Story");
			put("47-story", "47 Story");
			put(" building: 46 levels", "46 Story");
			put("45th floor ", "45 Story");
			put("ranch, reverse ranch,", "Reverse Ranch");
			put(" colonial |Spanish Colonial|The Colonial| Gorgeous colonial |Traditional Colonia|Colonial and Ranch|colonial home plans|building colonial|and Colonial|Colonial</h3>|Colonial Homes|Colonial Revival|Ranch-, Colonial-|Colonial- and Ranch|Colonial styles|story colonial|Ranch or Colonial|ranches to colonials|Colonial Single-Family Homes|Traditional Colonial|, Colonial and|Crafts, Colonial, |Colonial Architecture|Ranch, Colonial and|Colonial inspired architecture|Colonial Village|At Colonial Charters|Colonial exterior|including Colonial| Craftsman Colonial |</span>Colonial</span>|colonial, art |Colonial settlements|Spanish colonial Reviva|, Colonial Estates|Spanish Colonial, Ranch| colonial design|Colonial\\s+</span>",
					"Colonial");
		}
	};

	public static void log(Object o) {

		System.out.println(o);
	}

	public static String getCacheFileName(String url) {

		String str = url.replaceAll("http://", "");
		str = str.replaceAll("www.", "");
		str = str.replaceAll("[^\\w]", "");
		if (str.length() > 200) {
			str = str.substring(0, 100) + str.substring(170, 190) + str.length() + "-" + str.hashCode();

		}

		try {
			str = URLEncoder.encode(str, "UTF-8");
			// U.log(str);

		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();

		}
		return str + ".txt";
	}

	public static String getnote(String html) {
		html = html.replaceAll(" {2,} ", "");

		String note = "-", match;
		String pnote = "-";
		for (String item : notes) {
			// U.log(html);
			match = Util.match(html, item, 0);

			if (match != null) {
				// // U.log(html);
				// U.log("::kirti");
				html = html.toUpperCase().replace(item.toUpperCase(), "");
				// U.log(html);
				U.log("item :" + item);
				note = U.getCapitalise(match.toLowerCase());
				if (pnote.equals("-"))
					pnote = note;
				else
					pnote = pnote + ", " + note;
			}

		}
		return pnote;
	}

	private static ArrayList<String> notes = new ArrayList<String>() {
		{
			add("100% leased");
//			add("Presale");
			add("PRESALES EARLY 2023");
			add("presale begin early 2023");
			add("presale early 2023");
			add("PHASE 1 NOW OPEN FOR PRESALE");
			add("Phase 2 is now for sale");
//			add("Opening for Sales Fall 2021");
			add("PRECONSTRUCTION PRICING");
			add("Pre-Construction Opportunity");
			add("Just Released for Sale");
			add("Pre-Grand Opening Pricing Now");
			add("Lot For Sale");
			add("Final Home for Sale");
			add("Pre-Grand Opening Phase Release|Pre Grand Opening|Pre-Grand Opening");
			add("Now Open for Sales|now open for leasing|Leasing Late Summer 2020|Leasing 2020");
			add("New Town Homes for Sale|opening for sales January 2020|open for sales Fall 2019|Opening for Sales|RE-OPEN FOR SALES");
			add("Open Now For sales");
			add("Now leasing newest phase|Leasing 2022|PRELEASING 2017|Now Leasing|Lease Now|NOW LEASING|pre-leasing spring 2019|PRE-LEASING|Pre-Lease|NOW PRE-LEASING");
			add("Now Pre-Selling Phase II|Now Pre-Selling New Phase|Phase 2 Now Pre-Selling|Pre-sales coming Summer 2022|Pre-Selling Begins Summer 2022|Pre-sales starting June 1|Pre-Selling Fall 2022|now pre-selling|Pre-Selling|Now open for pre-sales|now open for pre-sale|pre-sales|Now Pre-Selling Phase 2|NOW PRE-SELLING PHASE II|Beginning Pre-Sales December 2019|Pre-selling|Pre-Selling This Fall|Pre-Selling late 2019|Pre-selling March 15|Now Pre-Selling Phase 4|Pre-Selling October 2019|Pre-selling November 2019|Now Pre-Selling Section 2|Pre-Selling New Section|NEW SECTION NOW PRE-SELLING|New Phase Pre-Selling in Spring|Pre-sales 2 available homesites|Now Pre-Selling New Phase|New Phase Now Preselling|Now Pre-Selling Final Section|NOW PRE-SELLING NEW PHASE|Now Pre-Selling Final Phase|Now Pre-Selling Phase II|PRESALES JULY 2019|Now Pre-Selling Phase II|Preselling Fall 2017|Waterfront lots available for presale|Now Pre-Selling Phase II|pre-selling February 2020|New Phase Now Pre-Selling|NOW OPEN FOR PRESALES|NOW OPEN FOR PRE-SALE|Pre-Selling New Phase|Preselling Phase 4|Now Pre-Selling|now pre selling|Final Phase Now Preselling|Pre-Selling NOW|Now Pre-Selling| NOW PRE-SELLING| Now Pre-Selling |Now pre-selling|Now Pre-Selling|Final Phase Now Preselling|Now Preselling New Phase|Presales now open|Now Preselling|Now Pre-Selling|Now Pre-Selling|PRE-SELLING|PRE-SELLING|PRE-SELLING|Preselling Now|now preselling|Preselling|Pre Selling|Pre-Sell");
			add("pre-construction sales");
			add("Phase II is now open!!");
			// add("Pre-Construction Opportunity|PRE-CONSTRUCTION PRICING NOW
			// AVAILABLE|pre-construction prices|Pre-Construction Pricing|Pre-Construction
			// Pricing ?|Pre Construction Pricing ?");
			add("Now accepting home site reservations");
//			add("Leasing Early 2022");
//			add("Leasing Summer 2022");
//			add("Leasing 2022");
		}
	};

	public static String getCache(String path) throws MalformedURLException {

		String Dname = null;
		String host = new URL(path).getHost();
		host = host.replace("www.", "");
		int dot = host.indexOf("/");
		Dname = (dot != -1) ? host.substring(0, dot) : host;

		File folder = new File(U.getCachePath() + Dname);
		if (!folder.exists())
			folder.mkdirs();
		String fileName = getCacheFileName(path);
		fileName = U.getCachePath() + Dname + "/" + fileName;
		return fileName;
	}

	public static String post(String urlPath, HashMap<String, String> customHeaders, String data) throws Exception {

		// ----- Do the cookies
		HashSet<String> cookies = new HashSet<String>();

		{
			if (customHeaders != null) {
				URLConnection urlConn = getConn(customHeaders.get("Referer"), customHeaders);
				// U.log("Getting cookies from: " +
				// customHeaders.get("Referer"));
				cookies = CookieManager.getCookies(urlConn);
			}
		}

		// Send data
		URLConnection conn = getConn(urlPath, customHeaders);
		CookieManager.setCookies(conn, cookies);

		OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream());
		wr.write(data);
		wr.flush();
		StringBuffer buf = new StringBuffer();
		// Get the response
		BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
		String line;
		while ((line = rd.readLine()) != null) {
			buf.append(line).append("\n");
		}
		wr.close();
		rd.close();

		String html = buf.toString();

		return html;

	}

	/**
	 * @author Parag Humane
	 * @param args
	 */
	//
	//
	//

	public static String[] getAbsoluteLinks(String[] values, String baseUrl) {

		String[] Links = new String[values.length];
		for (int i = 0; i < values.length; i++) {
			if (values[i].startsWith("http"))
				Links[i] = values[i];
			else
				Links[i] = baseUrl + values[i];
		}
		return Links;
	}

	public static String getAbsoluteLink(String value, String baseUrl) {

		String link = null;
		if (value.startsWith("http"))
			link = value;
		else
			link = baseUrl + value;

		return link;
	}

	/**
	 * @param args
	 * @throws IOException
	 */
	// New function that mimics browser

	public static String getHtml(String url, WebDriver driver, Boolean flag) throws Exception {
		// WebDriver driver = new FirefoxDriver();
		String html = null;
		String Dname = null;
		String host = new URL(url).getHost();
		host = host.replace("www.", "");
		int dot = host.indexOf("/");
		Dname = (dot != -1) ? host.substring(0, dot) : host;
		File folder = null;
		if (flag == true)
			folder = new File(U.getCachePath() + Dname + "Quickkk");
		else
			folder = new File(U.getCachePath() + Dname);
		if (!folder.exists())
			folder.mkdirs();
		String fileName = U.getCacheFileName(url);

		// fileName = "C:/cache/" + Dname + "/" + fileName;
		if (flag == true)
			fileName = U.getCachePath() + Dname + "Quickkk" + "/" + fileName;
		else {
			fileName = U.getCachePath() + Dname + "/" + fileName;
		}

		File f = new File(fileName);
		if (f.exists())
			return html = FileUtil.readAllText(fileName);
		if (!f.exists()) {

			BufferedWriter writer = new BufferedWriter(new FileWriter(f));

			driver.get(url);

			((JavascriptExecutor) driver).executeScript("window.scrollBy(0,3000)", ""); // y value '400' can be

			try {
				WebElement option = null;

				option = driver.findElement(By.id("cntQMI"));// --------//*[@id="cntQMI"]
				option.click();
				Thread.sleep(3000);
				option.click();
				Thread.sleep(3000);
				U.log("::::::::::::click succusfull1:::::::::");

			} catch (Exception e) {
				U.log("click unsuccusfull1" + e.toString());
			}

			U.log("Current URL:::" + driver.getCurrentUrl());
			html = driver.getPageSource();
			Thread.sleep(2 * 1000);

			writer.append(html);
			writer.close();

		} else {
			if (f.exists())
				html = FileUtil.readAllText(fileName);
		}

		return html;

	}

	public static String getHtmlWithChromeBrowser(String url, WebDriver driver) throws Exception {
		// WebDriver driver = new ChromeDriver();

		String html = null;
		String Dname = null;

		String host = new URL(url).getHost();
		host = host.replace("www.", "");
		int dot = host.indexOf("/");
		Dname = (dot != -1) ? host.substring(0, dot) : host;
		File folder = null;

		folder = new File(U.getCachePath() + Dname);
		if (!folder.exists())

			folder.mkdirs();
		String fileName = U.getCacheFileName(url);

		fileName = U.getCachePath() + Dname + "/" + fileName;

		File f = new File(fileName);
		if (f.exists()) {
			return html = FileUtil.readAllText(fileName);
			// U.log("Reading done");
		}

		// int respCode = CheckUrlForHTML(url);

		// if(respCode==200)
		{

			if (!f.exists()) {
				synchronized (driver) {

					BufferedWriter writer = new BufferedWriter(
							new FileWriter(f));/*
												 * driver.manage() .addCookie( new Cookie("visid_incap_612201",
												 * "gt5WFScsSRy46ozKP+BwUyrx4FcAAAAAQUIPAAAAAADA5A7HU2IYoId7VKl8vCPR"));
												 */
					driver.get(url);
					Thread.sleep(5000);
					((JavascriptExecutor) driver).executeScript("window.scrollBy(0,400)", ""); // y value '400' can
					// be

					// WebElement click =
					// driver.findElement(By.xpath("//*[@id=\"reset-available-homes\"]"));
					// click.click();

					Thread.sleep(2000);
					U.log("Current URL:::" + driver.getCurrentUrl());
					html = driver.getPageSource();
					Thread.sleep(2000);
					writer.append(html);
					writer.close();

				}
			} else {
				if (f.exists()) {
					html = FileUtil.readAllText(fileName);
					U.log("Reading done");
				}
			}
			return html;
		}
		// else{
		// return null;
		// }
	}

	public static void getHtmlDelete(String url, WebDriver driver) throws Exception {
		// WebDriver driver = new FirefoxDriver();

		String html = null;
		String Dname = null;

		String host = new URL(url).getHost();
		host = host.replace("www.", "");
		int dot = host.indexOf("/");
		Dname = (dot != -1) ? host.substring(0, dot) : host;
		File folder = null;

		folder = new File(U.getCachePath() + Dname);
		if (!folder.exists())
			folder.mkdirs();

		String fileName = U.getCacheFileName(url);

		fileName = U.getCachePath() + Dname + "/" + fileName;

		File f = new File(fileName);

		if (f.delete()) {
			log("Successfully Deleted=======");
		}

	}

	public static String getHtml(String url, WebDriver driver) throws Exception {
		// WebDriver driver = new FirefoxDriver();

		String html = null;
		String Dname = null;

		String host = new URL(url).getHost();
		host = host.replace("www.", "");
		int dot = host.indexOf("/");
		Dname = (dot != -1) ? host.substring(0, dot) : host;
		File folder = null;

		folder = new File(U.getCachePath() + Dname);
		if (!folder.exists())
			folder.mkdirs();

		String fileName = U.getCacheFileName(url);

		fileName = U.getCachePath() + Dname + "/" + fileName;

		File f = new File(fileName);
		if (f.exists()) {
			return html = FileUtil.readAllText(fileName);
			// U.log("Reading done");
		}

		// if(respCode==200)
		{

			if (!f.exists()) {
				synchronized (driver) {

					BufferedWriter writer = new BufferedWriter(new FileWriter(f));
//					Thread.sleep(10000);
					driver.get(url);

					// U.log("after::::"+url);
					Thread.sleep(5000);
					((JavascriptExecutor) driver).executeScript("window.scrollBy(0,15000)", "");
					Thread.sleep(3000);
					// U.log("Current URL:::" + driver.getCurrentUrl());
					html = driver.getPageSource();
					Thread.sleep(2000);

					writer.append(html);
					writer.close();

				}
			} else {
				if (f.exists()) {
					html = FileUtil.readAllText(fileName);
					U.log("Reading done");
				}
			}
			return html;
		}
		// else{
		// return null;
		// }
	}

	public static String getHtml(String url, WebDriver driver, String id) throws Exception {

		String html = null;
		String Dname = null;
		String host = new URL(url).getHost();
		host = host.replace("www.", "");
		int dot = host.indexOf("/");
		Dname = (dot != -1) ? host.substring(0, dot) : host;
		File folder = null;
		folder = new File(U.getCachePath() + Dname);
		if (!folder.exists())
			folder.mkdirs();
		String fileName = U.getCacheFileName(url);
		fileName = U.getCachePath() + Dname + "/" + fileName;
		File f = new File(fileName);
		if (f.exists())
			return html = FileUtil.readAllText(fileName);
		if (!f.exists()) {
			synchronized (driver) {

				BufferedWriter writer = new BufferedWriter(new FileWriter(f));
				U.log("in gethtml==" + url);
				driver.get(url);
				Thread.sleep(5000);
				Actions dragger = new Actions(driver);
				WebElement draggablePartOfScrollbar = driver.findElement(By.id(id));
				int numberOfPixelsToDragTheScrollbarDown = 50;
				for (int i = 10; i < 500; i = i + numberOfPixelsToDragTheScrollbarDown) {
					try {
						dragger.moveToElement(draggablePartOfScrollbar).click()
								.moveByOffset(0, numberOfPixelsToDragTheScrollbarDown).release().perform();
						Thread.sleep(1000L);
					} catch (Exception e1) {
					}
				}
				U.log("Current URL:::" + driver.getCurrentUrl());
				html = driver.getPageSource();
				writer.append(html);
				writer.close();
			}
		} else {
			if (f.exists())
				html = FileUtil.readAllText(fileName);
		}
		return html;

	}

	public static int CheckUrlForHTML(String path) {
		// TODO Auto-generated method stub
		int respCode = 0;
		try {

			DefaultHttpClient httpclient = new DefaultHttpClient();
			HttpPost request1 = new HttpPost(path);
			HttpResponse response1 = httpclient.execute(request1);
			respCode = response1.getStatusLine().getStatusCode();
		} catch (Exception ex) {

			U.log(ex);
			return respCode;
		}
		return respCode;
	}

	public static String getHTML(String path) throws IOException {

		path = path.replaceAll(" ", "%20");
		// U.log(" .............."+path);
		// Thread.sleep(4000);
		String fileName = getCache(path);
		File cacheFile = new File(fileName);
		if (cacheFile.exists())
			return FileUtil.readAllText(fileName);

		URL url = new URL(path);
		U.log(url);
		String html = null;

		// chk responce code

//		int respCode = CheckUrlForHTML(path);
//		 U.log("respCode=" + respCode);
//		 if (respCode == 200) {

		// Proxy proxy = new Proxy(Proxy.Type.HTTP, new //"157.90.199.133", 1080)
		// InetSocketAddress("107.151.136.218",80 )); //"181.215.130.32", 8080)
		// //"134.209.69.46", 8080 //"157.90.199.129", 1080)
		Proxy proxy = new Proxy(Proxy.Type.HTTP, new InetSocketAddress("104.149.3.3", 8080));
		final URLConnection urlConnection = url.openConnection();
		// Mimic browser
		try {
			urlConnection.addRequestProperty("User-Agent",
					"Mozilla/5.0 (X11; Linux x86_64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/101.0.4951.64 Safari/537.36");
			urlConnection.addRequestProperty("accept", "application/json, text/javascript, */*; q=0.01");
			urlConnection.addRequestProperty("accept-language", "en-GB,en-US;q=0.9,en;q=0.8");
			urlConnection.addRequestProperty("cache-control", "no-cache");
			urlConnection.addRequestProperty("referer", "https://www.c-rock.com/communities-austin");
			urlConnection.addRequestProperty("authority", "tiles.regrid.com");
			urlConnection.addRequestProperty("path", "/api/v1/parcels/16/10594/25363.json");
			urlConnection.addRequestProperty("origin", "https://app.regrid.com");
			urlConnection.addRequestProperty("cookie",
					"_fbp=fb.1.1646369270108.61888341; visitor_id478992=405823352; visitor_id478992-hash=9320b3a9fe77e6f9929f33c97de004c25f1cc196677379fc49a3d33b0019bbe074c83de8fe25d91ba54d304f47441dd2ac26a9b7; _gcl_au=1.1.404625297.1655698270; _gid=GA1.2.1837601758.1655698270; dm_timezone_offset=-330; dm_last_visit=1655698270330; dm_total_visits=7; _gat_UA-34098240-1=1; _gat_gtag_UA_178855985_1=1; dm_last_page_view=1655698556846; dm_this_page_view=1655698565752; _ga_HW6GTCCMQT=GS1.1.1655698270.13.1.1655698565.0; _ga=GA1.1.888920987.1646369268; _sp_id.d2cb=69a90e2e58ac7477.1646369269.7.1655698566.1646629494; _sp_ses.d2cb=1655700366248");
			// U.log("getlink");
			final InputStream inputStream = urlConnection.getInputStream();

			html = IOUtils.toString(inputStream);
//		    U.log(html);

			// final String html = toString(inputStream);
			inputStream.close();
			if (!cacheFile.exists())
				FileUtil.writeAllText(fileName, html);

			return html;
		} catch (Exception e) {
			U.log("gethtml expection: " + e);

		}
		return html;
		/*
		 * } else { return null; }
		 */

	}

	private static HttpURLConnection getConn(String urlPath, HashMap<String, String> customHeaders) throws IOException {

		URL url = new URL(urlPath);
		HttpURLConnection conn = (HttpURLConnection) url.openConnection();
		conn.setDoOutput(true);
		conn.addRequestProperty("User-Agent",
				"Mozilla/5.0 (Windows NT 6.1; WOW64; rv:10.0.2) Gecko/20100101 Firefox/10.0.2");
		conn.addRequestProperty("Accept", "text/css,application/xhtml+xml,application/xml,*/*;q=0.9");
		conn.addRequestProperty("Accept-Language", "en-us,en;q=0.5");
		conn.addRequestProperty("Cache-Control", "max-age=0");
		conn.addRequestProperty("Connection", "keep-alive");

		if (customHeaders == null || !customHeaders.containsKey("Referer")) {
			conn.addRequestProperty("Referer", "http://" + url.getHost());
		}
		if (customHeaders == null || !customHeaders.containsKey("Host")) {
			conn.addRequestProperty("Host", url.getHost());
		}

		if (customHeaders != null) {
			for (String headerName : customHeaders.keySet()) {
				conn.addRequestProperty(headerName, customHeaders.get(headerName));
			}
		}

		return conn;
	}// getConn

	private static String toString(InputStream inputStream) throws IOException {

		BufferedReader in = new BufferedReader(new InputStreamReader(inputStream));
		String line;

		StringBuilder output = new StringBuilder();
		while ((line = in.readLine()) != null) {
			output.append(line).append("\n");
		}
		in.close();
		return output.toString();
	}

	// Original Parag's code
	public static String getHTML2(String path) {

		StringBuilder output = new StringBuilder();

		try {

			URL myUrl = new URL(path);
			BufferedReader in = new BufferedReader(new InputStreamReader(myUrl.openStream()));

			String line;

			while ((line = in.readLine()) != null) {
				output.append(line).append("\n");
				// System.out.println(line);
			}
			// System.out.println(line);

			in.close();

		} catch (IOException ex) {
		} finally {
		}

		return output.toString();
	}

	public static boolean isEmpty(String o) {

		return (o == null || o.trim().length() == 0);
	}

	public static String getHtmlSection(String code, String From, String To) {

		String section = null;
		int start, end;
		start = code.indexOf(From);
		if (start != -1) {
			end = code.indexOf(To, start + From.length());
			if (start < end)
				section = code.substring(start, end);
		}
		return section;
	}

	public static String removeSectionValue(String code, String From, String To) {
		String section = null;
		int start, end;
		start = code.indexOf(From);
		if (start != -1) {
			end = code.indexOf(To, start + From.length());
			if (start < end)
				section = code.replace(code.substring(start + From.length(), end), "");
			// U.log("%%%%%%%%%%%%%"+"Successfully Remove Section"+"%%%%%%%%%%%%%%%%%");
		} else {
			section = code;
		}
		return section;
	}

	//
	public static String getSectionValue(String code, String From, String To) {

		String section = null;
		int start, end;
		start = code.indexOf(From);
		// U.log(start);
		if (start != -1) {
			end = code.indexOf(To, start + From.length());
			if (start < end)
				section = code.substring(start + From.length(), end);

		}
		return section;

	}

	public static String getCachePath() {
		String Regex = "Cache";
		String Filename = System.getProperty("user.home");
		// U.log(Filename+"filename");
		if (Filename.contains("/")) {
			Regex = "/Cache/";
		} else {
			Regex = "\\Cache\\";
		}
		Filename = Filename.concat(Regex);
		// U.log("filename :::"+Filename);
		if (!Filename.equals(Regex)) {
			Regex = Regex.toLowerCase();
		}
		return Filename;
	}

	public static String[] getValues(String code, String From, String To) {

		ArrayList<String> al = new ArrayList<String>();
		int n = 0;
		String value = null;
		while (n != -1) {
			int start = code.indexOf(From, n);

			if (start != -1) {
				int end = code.indexOf(To, start + From.length());

				try {
					if (end != -1 && start < end && end < code.length())
						value = code.substring(start + From.length(), end);
				} catch (StringIndexOutOfBoundsException ex) {
					n = end;
					continue;
				}

				al.add(value);
				n = end;
			} else
				break;
		}

		Object ia[] = al.toArray();
		String[] values = new String[ia.length];

		for (int i = 0; i < values.length; i++)
			values[i] = ia[i].toString();

		return values;

	}

	public static String[] getValues(String code, String begin, String From, String To) {

		ArrayList<String> al = new ArrayList<String>();
		int n = 0;
		n = code.indexOf(begin, n);
		String value = null;
		while (n != -1) {
			int start = code.indexOf(From, n);

			if (start != -1) {
				int end = code.indexOf(To, start + From.length());

				try {
					if (end != -1 && start < end && end < code.length())
						value = code.substring(start + From.length(), end);
				} catch (StringIndexOutOfBoundsException ex) {
					n = end;
					continue;
				}

				al.add(value);
				n = end;
			} else
				break;
		}

		Object ia[] = al.toArray();
		String[] values = new String[ia.length];

		for (int i = 0; i < values.length; i++)
			values[i] = ia[i].toString();

		return values;

	}

	public static String[] toArray(ArrayList<String> list) {

		Object ia[] = list.toArray();
		String[] strValues = new String[ia.length];
		for (int i = 0; i < strValues.length; i++)
			strValues[i] = ia[i].toString();
		return strValues;
	}

	public static void printFile(String[] lines, String fileName) {

		Writer output = null;

		try {
			output = new BufferedWriter(new FileWriter(fileName, true));
			for (int i = 0; i < lines.length; i++)
				output.write(lines[i] + "\n");

			output.close();

		} catch (IOException ex) {
		} finally {
		}

	}

	public static void printFile(ArrayList<String> list, String fileName) {

		Writer output = null;

		try {
			output = new BufferedWriter(new FileWriter(fileName, true));

			for (String item : list)
				output.write(item + "\n");

			output.close();

		} catch (IOException ex) {
		} finally {
		}

	}

	public static String degToDecConverter(String str) {

		double s1, s3, s4;
		double val;
		String s2;
		s1 = Integer.parseInt(str.substring(0, str.indexOf("&deg;")));
		s2 = str.substring(str.indexOf(";"));
		s3 = Integer.parseInt(s2.substring(2, s2.indexOf("'")));
		s4 = Integer.parseInt(str.substring(str.length() - 3, str.indexOf("\"")));

		val = s1 + (s3 / 60) + (s4 / 3600);
		return Double.toString(val);
	}

	public static String[] getAddressFromLines(String[] lines) {

		final String BLANK = "-";
		String street = BLANK, city = BLANK, state = BLANK, zip = BLANK;
		String addStreet = "-";
		int len = lines.length;
		if (len >= 1) {
			for (int i = 0; i <= (len - 2); i++)
				addStreet = lines[i] + ",";
			street = addStreet;
			if (lines[len - 1].indexOf(",") != -1) {
				city = lines[len - 1].split(",")[0];
				String add = lines[len - 1].split(",")[1];
				add = add.trim();
				state = add.substring(0, 2).toUpperCase();
				zip = Util.match(add, "\\d{5}?", 0);
				if (zip == null)
					zip = BLANK;
			}

		}
		String[] add = { street, city, state, zip };
		return add;
	}

	/**
	 * @author Parag Humane
	 * @param args
	 */
	public static int[] getMaxAndMin(int[] a) {

		int Max = a[0], Min = a[0];
		for (int i = 0; i < a.length; i++) {
			if (a[i] > Max)
				Max = a[i];
			else if (a[i] < Min) {
				Min = a[i];
			}
		}
		int[] arr = new int[2];
		arr[0] = Max;
		arr[1] = Min;
		return arr;
	}

	/**
	 * @author Parag Humane
	 * @param args
	 */

	private static int[] getIntMaxMin(String code, String regExp, int group) {

		ArrayList<String> list = Util.matchAll(code, regExp, group);
		ArrayList<Integer> intList = new ArrayList<Integer>();
		int val;
		boolean isPrice = false;
		for (int i = 0; i < list.size(); i++) {
			isPrice = list.get(i).contains("$");
			String str = list.get(i).replaceAll(",|\\$", "");
			ArrayList<String> intTemp = Util.matchAll(str, "\\d{3,}", 0);
			for (String item : intTemp) {

				val = Integer.valueOf(item);
				if (isPrice && val > 20000)
					intList.add(val);
				if (!isPrice && val > 0)
					intList.add(val);

			}
		}

		int[] intVal = new int[intList.size()];
		for (int i = 0; i < intList.size(); i++) {
			intVal[i] = intList.get(i);
			// /U.log(intList.get(i))
			;
		}
		if (intVal.length != 0) {
			intVal = U.getMaxAndMin(intVal);
			if (intVal[1] == 0 && intVal[0] != 0)
				return new int[] { intVal[0], intVal[1] };
			return new int[] { intVal[1], intVal[0] };
		}
		return new int[] { 0, 0 };
	}

	/**
	 * @author Parag Humane
	 * @param args
	 */
	public static String[] getPrices(String code, String regExp, int group) {

		int[] values = getIntMaxMin(code, regExp, group);
//		 U.log(values[0]+ "  "+ values[1]);
		String minPrice = NumberFormat.getCurrencyInstance(Locale.US).format(values[0]).replaceAll("\\.00", "");
		String maxPrice = NumberFormat.getCurrencyInstance(Locale.US).format(values[1]).replaceAll("\\.00", "");
//		 U.log(minPrice+ "  "+ maxPrice);
		minPrice = (values[0] > 20000) ? minPrice : null;
		maxPrice = (values[1] > 20000) ? maxPrice : null;
		if (maxPrice != null) {
			if (maxPrice.equals(minPrice))
				maxPrice = null;
		}
		if (minPrice == null && maxPrice != null) {
			minPrice = maxPrice;
			maxPrice = null;
		}

		return new String[] { minPrice, maxPrice };
	}

	/**
	 * @author Parag Humane
	 * @param args
	 */
	public static String getHTML(String path, String baseUrl) throws IOException {

		if (baseUrl != null && !path.startsWith(baseUrl))
			return getHTML(baseUrl + path);
		else
			return getHTML(path);
	}

	/**
	 * @author Parag Humane
	 * @param args
	 */
	public static String[] getSqareFeet(String code, String regExp, int group) {

		int[] values = getIntMaxMin(code, regExp, group);
		String minSqf = (values[0] != 0) ? "" + values[0] : null;
		String maxSqf = (values[1] != 0) ? "" + values[1] : null;

		if (maxSqf != null) {
			if (maxSqf.equals(minSqf))
				maxSqf = null;
		}

		return new String[] { minSqf, maxSqf };
	}

	/**
	 * @author Parag Humane
	 * @param args
	 */
	public static String getPropertyTypes(String code, String regExp, int group) {

		ArrayList<String> list = Util.matchAll(code, regExp, group);
		ArrayList<String> temp = new ArrayList<String>();
		StringBuilder type = null;

		if (list.size() != 0) {
			for (String item : list) {
				item = item.trim();
				if (item.equals("�") || item.length() == 0)
					continue;
				if (!temp.contains(item)) {
					temp.add(item);
					if (type == null) {
						type = new StringBuilder();
						type.append(item);
					} else
						type.append(", " + item);
				}
			}

		}

		if (type == null) {
			type = new StringBuilder();
		} else if (type.length() > 50) {
			String pType = type.toString();
			pType = pType.substring(0, 51);
			pType = Util.match(pType, "(.*?,)+", 0);
			pType = pType.substring(0, pType.length() - 1);
			type = new StringBuilder(pType);
		}
		return type.toString();
	}// /

	/**
	 * @author Parag Humane
	 * @param args
	 */
	public static String[] getGooleLatLong(String html) {

		String reg = "\\{center:\\{lat:(\\d+\\.\\d+),lng:(-\\d+\\.\\d+)";
		String reg2 = "<title>\\s*(\\d+\\.\\d+),\\s*(-\\d+\\.\\d+)";
		String match = Util.match(html, reg, 0);
		reg = (match == null) ? reg2 : reg;
		String lat = Util.match(html, reg, 1);
		String lng = Util.match(html, reg, 2);
		return new String[] { lat, lng };
	}

	public static String[] getGlatlng(String[] add) throws Exception {
		// String addr = add[0] + "+" + add[1];
		String addr = add[0] + "+" + add[1] + "+" + add[2] + "+" + add[3];
		String data = null;
		String latLong = null;
		StringBuffer input = new StringBuffer();
		String link = "https://maps.google.com/maps?daddr=" + URLEncoder.encode(addr, "UTF-8");

		URL url = new URL(link);

		HttpURLConnection connection = (HttpURLConnection) url.openConnection();
		BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream()));

		while ((data = br.readLine()) != null) {
			input.append(data);
		}

		String path = "E:\\google.txt";
		File file = new File(path);
		FileWriter wr = new FileWriter(file);
		wr.write(input.toString());
		int x = input.indexOf("{lat:");
		int y = input.indexOf("}", x);
		U.log("X:" + x);
		U.log("Y:" + y);

		latLong = input.substring(x, y);

		latLong = latLong.replace("{lat:", "");
		latLong = latLong.replace("lng:", "");

		String[] array = latLong.split(",");
		return array;
	}

	public static String[] getGooleLatLong(String[] add) throws IOException {

		String addr = add[0] + "+" + add[1] + "+" + add[2] + "+" + add[3];
		addr = "https://maps.google.com/maps?daddr=" + URLEncoder.encode(addr, "UTF-8");
		U.log(addr);
		String html = U.getHTML(addr);
		return U.getGooleLatLong(html);
	}

	public static String[] getAddress(String line) {

		String[] ad = line.split(",");

		String[] add = new String[] { "-", "-", "-", "-" };
		int n = ad.length;
		// U.log("ad length is ::"+ad.length);
		if (ad.length >= 3) {
			for (int i = 0; i < n - 2; i++)
				add[0] = (i == 0) ? ad[i].trim() : add[0] + ", " + ad[i].trim();
			add[0] = add[0].trim().replaceAll("\\d+.\\d+\\s*,\\s*-\\d+.\\d+", "");

			add[1] = ad[n - 2].trim();// city
			add[2] = Util.match(ad[n - 1], "\\w+", 0); // State--or-->\\w+ OR
														// [a-z\\sa-z]+
			add[2] = (add[2] == null) ? "-" : add[2].toUpperCase();
			if (add[2] == null)
				add[2] = "-";
			if (add[2].length() > 2)
				add[2] = USStates.abbr(add[2]);
			add[3] = Util.match(ad[n - 1], "\\d{5}", 0);
			if (add[3] == null)
				add[3] = "-";
		}
		for (int i = 0; i < 1; i++) {
			if (!add[i].equals("-") && add[i].length() < 3)
				add[i] = "-";
		}

		return add;
	}

	public static String[] getMapQuestAddress(String html) {

		String section = U.getSectionValue(html, "singleLineAddress\":\"", "\"");
		String[] add = null;
		if (section != null)
			add = U.getAddress(section);
		return add;
	}

	public static String[] getMapQuestLatLong(String html) {

		String lat = null, lng = null;
		String reg = "\"latLng\":\\{\"lng\":(-\\d+.\\d+),\"lat\":(\\d+.\\d+)";
		String reg2 = "\"latLng\":\\{\"lat\":(\\d+.\\d+),\"lng\":(-\\d+.\\d+)";
		String match = Util.match(html, reg, 0);
		if (match == null) {
			lat = Util.match(html, reg2, 1);
			lng = Util.match(html, reg2, 2);
		} else {
			lat = Util.match(html, reg, 2);
			lng = Util.match(html, reg, 1);
		}
		return new String[] { lat, lng };
	}

	public static String[] findAddress(String code) {

		String[] add = null;
		String sec = formatAddress(code);
		// U.log("sec::::"+sec);
		String regAddress = "\\d*\\s*\\w+[^,\n]+[,\n]+\\s*\\w+[\\s,]+\\w+.*?\\d{5}";
		String reg2 = "\\d+\\s*\\w+[^,\n]+[,\n]+\\s*\\w+[^,]+,\\s*\\w+\\s*\\d{5}";
		String reg4 = "\\s*\\w+[^,\n]+[,\n]+\\s*\\w+[^,]+,\\s*\\w+\\s*\\d{5}";
		String reg3 = "\\d+[^,]+,( ?\\w+){1,5},( ?\\w+)( \\d{5})?";
		String match = Util.match(sec, regAddress, 0);

		if (match == null)
			match = Util.match(sec, reg2, 0);
		if (match == null)
			match = Util.match(sec, reg3, 0);
		if (match != null)
			add = U.getAddress(match);
		if (match != null)
			match = Util.match(sec, reg4, 0);

		return add;
	}

	public static String[] getGoogleAdd(String lat, String lon) throws Exception {
		String glink = "https://maps.google.com/maps?q=" + lat + "," + lon;
		U.log("Visiting :" + glink);
		// WebDriver driver=new FirefoxDriver();
		// driver.get(glink);
		String htm = U.getHTML(glink);
		U.log(U.getCache(glink));
		return U.getGoogleAddress(htm);
	}

	public static String formatAddress(String code) {

		String sec = code.replaceAll("\\&nbsp;|�|&#038;|�", " ");
		sec = sec.replaceAll(" {2,}", " ");

		sec = sec.replaceAll("\\s{2,}", "");

		sec = sec.replaceAll("<br>|<br[^>]+>|</p><p[^>]+>|</p>|</br>|<BR>|<BR />|<br />", ",");

		sec = sec.replaceAll("<[^>]+>", "");

		sec = sec.replaceFirst("[^>]+>", "");
		// U.log("sec:"+sec);
		// Priti-----

		if (sec.contains("Ave") && !sec.contains("Ave,") && !sec.contains("Avenue") && !sec.contains("Avenue,"))
			sec = sec.replace("Ave", "Ave,");
		if (sec.contains("Rd") && !sec.contains("Rd,") && !sec.contains("Rd."))
			sec = sec.replace("Rd", "Rd,");

		// -----------------------
		if (sec.contains("Dr.") && !sec.contains("Dr.,"))
			sec = sec.replace("Dr.", "Dr.,");
		if (sec.contains("Drive") && !sec.contains("Drive,"))
			sec = sec.replace("Drive", "Drive,");
		if (sec.contains("Trail") && !sec.contains("Trail,") && !sec.contains("Trails"))
			sec = sec.replace("Trail", "Trail,");
		if (sec.contains("Street") && !sec.contains("Street,") && !sec.contains("Street &"))
			sec = sec.replace("Street", "Street,");
		if (sec.contains("Blvd.") && !sec.contains("Blvd.,"))
			sec = sec.replace("Blvd.", "Blvd.,");
		if (sec.contains("Avenue") && !sec.contains("Avenue,"))
			sec = sec.replace("Avenue", "Avenue,");
		if (sec.contains("Road") && !sec.contains("Road,") && !sec.contains("Roads,"))
			sec = sec.replace("Road", "Road,");
		if (sec.contains("Ct") && !sec.contains("Ct,") && !sec.contains("Ct. #"))
			sec = sec.replace("Ct", "Ct,");
		if (!sec.contains("Lane <") && sec.contains("Lane") && !sec.contains("Lane,"))
			sec = sec.replace("Lane", "Lane,");
		if (sec.contains("land Court") && !sec.contains("land Court.,"))
			sec = sec.replace("land Court", "land Court,");
		if (sec.contains(", St.") && !sec.contains(", St.,"))
			sec = sec.replace(", St.", " St.,");
		return sec;
	}

	public static String[] getGoogleAddress(String html) throws IOException {

		String section = U.getSectionValue(html, "addr:'", "'");
		U.log("U section==" + section);
		String[] add = null;
		if (section != null) {
			add = U.getAddress(section.replaceAll("\\\\x26", "&"));
		}
		return add;
	}

	/**
	 * @author Parag Humane
	 * @param args
	 */
	public static String getPageSource(String url) throws IOException {

		String html = null;
		String fileName = U.getCache(url);
		File cacheFile = new File(fileName);
		if (cacheFile.exists())
			return FileUtil.readAllText(fileName);
		WebDriver driver = new HtmlUnitDriver();

		driver.get(url);
		html = driver.getPageSource();
		if (!cacheFile.exists())
			FileUtil.writeAllText(fileName, html);
		return html;
	}// //

	public static int countMatch(String reg, String text) {

		Matcher m = Pattern.compile(reg).matcher(text);
		int n = 0;
		while (m.find())
			n++;
		return n;
	}// //

	public static ArrayList<String> removeDuplicates(ArrayList<String> list) {

		HashSet<String> hs = new HashSet<String>();
		hs.addAll(list);
		list.clear();
		list.addAll(hs);
		hs = null;
		return list;
	}// ///

	static {
		try {
			bypassCertificate();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void bypassCertificate() throws Exception {
		TrustManager[] trustAllCerts = new TrustManager[] { new X509TrustManager() {
			public java.security.cert.X509Certificate[] getAcceptedIssuers() {
				return null;
			}

			public void checkClientTrusted(X509Certificate[] certs, String authType) {
			}

			public void checkServerTrusted(X509Certificate[] certs, String authType) {
			}

			@Override
			public void checkClientTrusted(java.security.cert.X509Certificate[] arg0, String arg1)
					throws java.security.cert.CertificateException {
				// TODO Auto-generated method stub

			}

			@Override
			public void checkServerTrusted(java.security.cert.X509Certificate[] arg0, String arg1)
					throws java.security.cert.CertificateException {
				// TODO Auto-generated method stub

			}

		} };

		// Install the all-trusting trust manager
		SSLContext sc = SSLContext.getInstance("SSL");
		sc.init(null, trustAllCerts, new java.security.SecureRandom());
		HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());

		// Create all-trusting host name verifier
		HostnameVerifier allHostsValid = new HostnameVerifier() {
			public boolean verify(String hostname, SSLSession session) {
				return true;
			}
		};

		// Install the all-trusting host verifier
		HttpsURLConnection.setDefaultHostnameVerifier(allHostsValid);
	}

	// ========================================================written new code by
	// Upendra===================================================
	static String propTypeIgnore = "[v|V]illage|[L|l]uxurious [O|o]wner(.*?)s [b|B]athroom";

	public static String getNewPropType(String html) throws Exception {
		html = getNoHtml(html);
		html = html.replaceAll("&nbsp;", " ").toLowerCase().replaceAll(propTypeIgnore.toLowerCase(), "");
		BufferedReader bufReader = new BufferedReader(new FileReader("resources/propTypes.txt"));
		String match, list = "";
		String line = bufReader.readLine();
		while (line != null) {
			if (line.contains("\"\""))
				throw new Exception("Check Property File in line " + line);
			line = line.replaceAll("\"\\s*,\\s*\"", "\",\"");
			String[] proeV = line.split("\",\"");
			// log("hello:->"+proeV[0]);
			match = Util.match(html, proeV[0].replace("\"", ""), 0);
			if (match != null) {
				proeV[1] = proeV[1].replace("\"", "");
				U.log("match Ptype::" + match);
				if (!list.contains(proeV[1])) {
					list = (list.length() == 0) ? proeV[1] : list + ", " + proeV[1];
				}
			}
			line = bufReader.readLine();
		}

		list = (list.length() < 4) ? "-" : list;
		bufReader.close();
		return list;
	}

	// ========================================================written new code by
	// Upendra===================================================
	private static final String dpropTypeIgnore = "branch";

	public static String getNewdCommType(String html) throws Exception {
		BufferedReader bufReader = new BufferedReader(new FileReader("resources/dcommTypes.txt"));

		String match, dType = "";
		String line = bufReader.readLine();
		while (line != null) {
			if (line.contains("\"\""))
				throw new Exception("Check DProperty File in line " + line);
			line = line.replaceAll("\"\\s*,\\s*\"", "\",\"");
			String[] proeV = line.split("\",\"");
			html = getNoHtml(html);
//				U.log(html.contains("ranch"));
			html = html.replaceAll("&nbsp;", " ").toLowerCase().replaceAll(dpropTypeIgnore.toLowerCase(), "");
			match = Util.match(html, proeV[0].replace("\"", "").trim(), 0);
			if (match != null) {
				proeV[1] = proeV[1].replace("\"", "");
				U.log("match::" + match);
				if (!dType.contains(proeV[1])) {
					dType = (dType.length() == 0) ? proeV[1] : dType + ", " + proeV[1];
				}
			}
			line = bufReader.readLine();
		}
		dType = (dType.length() < 4) ? "-" : dType;
		bufReader.close();
		return dType;
	}

	// ========================================================written new code by
	// Upendra===================================================
	public static String getNewPropStatus(String html) throws Exception {
		BufferedReader bufReader = new BufferedReader(new FileReader("resources/propStatus.txt"));
		html = getNoHtml(html);
		html = html.replaceAll(" {2,} ", "");
		html = html.replaceAll("&nbsp;", " ").toLowerCase().replaceAll(propStatusIgnore.toLowerCase(), "");
		// U.log("propstatushtml--------------------------------------"+html);
		String status = "-", match;
		String pStatus = "-";
		String line = bufReader.readLine();
		// log(line);
		while (line != null) {
			line = line.replace("\"", "");
			if (line.contains("\"\""))
				throw new Exception("Check Property status File  in line " + line);
			match = Util.match(html, line, 0);
			if (match != null) {
				U.log("item :" + line);
				html = html.toUpperCase().replace(line.toUpperCase(), "");
				status = U.getCapitalise(match.toLowerCase());
				if (pStatus.equals("-"))
					pStatus = status;
				else
					pStatus = pStatus + ", " + status;
			}
			line = bufReader.readLine();

		}
		bufReader.close();
		return pStatus;
	}

	public static String getPropType(String html) {
		HashSet<String> type = new HashSet<String>();
		String match, list = "", value;
		Iterator<?> i = propTypes.entrySet().iterator();
		while (i.hasNext()) {
			Map.Entry me = (Map.Entry) i.next();
			value = me.getValue().toString();
			html = html.replaceAll("Cabinet|cabinet", "");
			match = Util.match(html, me.getKey().toString(), 0);
			if (match != null) {
				U.log("match Ptype::" + match);
				if (!list.contains(value)) {
					list = (list.length() == 0) ? value : list + ", " + value;
				}
			}
		}
		list = (list.length() < 4) ? "-" : list;
		return list;
	}

	public static String getPropStatus(String html) {
		html = html.replaceAll(" {2,} ", "");
		html = html.replaceAll("&nbsp;", " ").toLowerCase().replaceAll(propStatusIgnore.toLowerCase(), "");
		// U.log("propstatushtml--------------------------------------"+html);
		String status = "-", match;
		String pStatus = "-";
		for (String item : propStatus) {
			// U.log(html);
			match = Util.match(html, item, 0);

			if (match != null) {
//				 U.log("match::"+match);
				// U.log("::kirti");
				html = html.toUpperCase().replace(item.toUpperCase(), "");
//				 U.log("html:::"+html);
				U.log("item :" + item);
				status = U.getCapitalise(match.toLowerCase());
				if (pStatus.equals("-"))
					pStatus = status;
				else
					pStatus = pStatus + ", " + status;
			}

		}
		return pStatus;
	}

	public static String getCommType(String html) {

		return U.getCommunityType(html);
	}

	public static String getdCommType(String html) {

		HashSet<String> type = new HashSet<String>();
		String match, list = "", value;
		Iterator<?> i = dcommTypes.entrySet().iterator();
		while (i.hasNext()) {
			Map.Entry me = (Map.Entry) i.next();
			value = me.getValue().toString();
			match = Util.match(html, me.getKey().toString(), 0);
			if (match != null) {
				U.log(value + "::::::::" + match);
				if (!list.equals(value)) {
					list = (list.length() == 0) ? value : list + ", " + value;
				}
			}
		}
		// U.log("list : " + list);
		list = (list.length() < 4) ? "-" : list;
		return list;
	}

	public static String getCommunityType(String html) {

		HashSet<String> type = new HashSet<String>();
		String match, list = "", value;
		Iterator<?> i = communityType.entrySet().iterator();
		while (i.hasNext()) {
			Map.Entry me = (Map.Entry) i.next();
			value = me.getValue().toString();

			match = Util.match(html, me.getKey().toString(), 0);
			if (match != null) {
				U.log("match ctype:::" + match);
				if (!list.contains(value)) {
					list = (list.length() == 0) ? value : list + ", " + value;
				}
			}
		}
		list = (list.length() < 4) ? "-" : list;
		return list;
	}

	public static String removeComments(String html) {
		String[] values = U.getValues(html, "<!--", "-->");
		for (String item : values)
			html = html.replace(item, "");
		return html;

	}

	public static boolean isvalideLatLng1(String add[], String lat, String lng) throws Exception {

		// FirefoxDriver driver2;
		if (lat != "" || lat == null) {
			String link = "https://maps.google.com/maps?q=" + lat + "," + lng;
			String htm = U.getHTML(link);
			String gAdd[] = U.getGoogleAddress(htm);
			U.log(gAdd[0] + " " + gAdd[1] + " " + gAdd[2] + " " + gAdd[3]);
			if (gAdd[3].equals(add[3]))
				return true;
			else if (gAdd[1].equalsIgnoreCase(add[1]) && gAdd[2].equalsIgnoreCase(add[2])) {
				return true;
			}

			else {
				// http://maps.yahoo.com/place/?lat=37.71915&lon=-121.845803&q=37.71915%2C-121.845803
				link = "http://maps.yahoo.com/place/?lat=" + lat + "&lon=" + lng + "&q=" + lat + "," + lng;

				driver = ShatamFirefox.getInstance();

				driver.open(link);
				driver.getOne("#yucs-sprop_button").click();
				htm = driver.getHtml();

				// driver2 = new FirefoxDriver();
				// driver2.
				String url = driver.getUrl();
				U.log(url);

//				URLDecoder decode = new URLDecoder();
				try {
					url = URLDecoder.decode(url, "UTF-8");
					U.log("Address:" + url);
					U.log(add[0] + " " + add[1] + " " + add[2] + " " + add[3]);
					if (url.contains(add[1]))
						return true;
					if (url.contains(add[3]))
						return true;
				} catch (UnsupportedEncodingException ex) {
					ex.printStackTrace();
				}
				// decode.decode(url);
				// U.log(url);
				// driver.close();
			}
			return false;
			// if(gAdd[1])
		}
		return false;
	}

	public static String[] getLatLngBingMap(String add[]) throws Exception {

		String addr = add[0] + " " + add[1] + " " + add[2] + " " + add[3];
		ShatamFirefox driver1 = null;
		String bingLatLng[] = { "", "" };
		driver1 = ShatamFirefox.getInstance();
		driver1.open("http://localhost/bing.html");
		driver1.wait("#txtWhere");
		driver1.getOne("#txtWhere").sendKeys(addr);
		driver1.wait("#btn");
		driver1.getOne("#btn").click();
		String binghtml = driver1.getHtml();
		// driver.wait(1000);
		U.log(binghtml);
		String secmap = U.getSectionValue(binghtml, "<div id=\"resultsDiv\"", "</body>");
		bingLatLng[0] = U.getSectionValue(secmap, "<span id=\"span1\">Latitude:", "<br>").trim();
		bingLatLng[1] = U.getSectionValue(secmap, "<br>Longitude:", "</span>");

		U.log("lat:" + bingLatLng[0]);
		U.log("lng:" + bingLatLng[1]);
		return bingLatLng;
	}

	public static String[] getBingAddress(String lat, String lon) throws Exception {
		String[] addr = null;
		String htm = U.getHTML("http://dev.virtualearth.net/REST/v1/Locations/" + lat + "," + lon
				+ "?o=json&jsonp=GeocodeCallback&key=Anqg-XzYo-sBPlzOWFHIcjC3F8s17P_O7L4RrevsHVg4fJk6g_eEmUBphtSn4ySg");
		String[] adds = U.getValues(htm, "formattedAddress\":\"", "\"");
		for (String item : adds) {
			addr = U.getAddress(item);
			if (addr == null || addr[0] == "-")
				continue;
			else {
				U.log("Bing Address =>  Street:" + addr[0] + " City :" + addr[1] + " state :" + addr[2] + " ZIP :"
						+ addr[3]);
				return addr;
			}

		}
		return addr;
	}

	public static String[] getBingLatLong(String[] address) throws Exception {
		String[] latLong = { null, null };
		if (address == null || address[0] == "-" || address[1] == "-" || address[2] == "-")
			return latLong;
		else {
			String line = address[0] + "," + address[1] + "," + address[2] + " " + address[3];
			try {
				latLong = getBingLatLong(line);
			} catch (java.io.IOException ex) {
				return latLong;
			}
		}
		return latLong;
	}

	public static String[] getBingLatLong(String addressLine) throws Exception {
		String[] latLong = new String[2];
		if (addressLine == null || addressLine.trim().length() == 0)
			return null;
		addressLine = addressLine.trim().replaceAll("\\+", " ");
		String geocodeRequest = "http://dev.virtualearth.net/REST/v1/Locations/'" + addressLine
				+ "'?o=xml&key=Anqg-XzYo-sBPlzOWFHIcjC3F8s17P_O7L4RrevsHVg4fJk6g_eEmUBphtSn4ySg";
		// U.log("-----------------addressline-----------"+geocodeRequest);
		String xml = U.getHTML(geocodeRequest);
		// U.log("--------------------------xml---------------------------------"+xml);
		latLong[0] = U.getSectionValue(xml, "<Latitude>", "</Latitude>");
		latLong[1] = U.getSectionValue(xml, "<Longitude>", "</Longitude>");
		return latLong;
	}

	public static boolean isMatchedLocation(String[] add, String[] addr) {
		boolean isMatched = false;
		for (int i = 0; i < 3; i++) {
			if (add[i] == null || addr[i] == null || add[i].equals("-") || addr[i].equals("-"))
				return isMatched;
		}

		if (addr[3].equals(add[3]))
			isMatched = true;
		else {
			if (addr[1].equalsIgnoreCase(add[1]) && addr[2].equalsIgnoreCase(add[2]))
				isMatched = true;
			else {
				if (addr[2].equalsIgnoreCase(add[2]) && add[3].substring(0, 3).equals(addr[3].substring(0, 3)))
					isMatched = true;
			}
		}
		return isMatched;
	}

	public static boolean isValidLatLong(String[] latLon, String[] add) throws Exception {
		boolean isValid = false;
		if (latLon == null || latLon[1] == null || add == null)
			return isValid;

		U.log("lat :" + latLon[0] + " Long :" + latLon[1]);

		if (latLon[0] == "-" || latLon[1] == "-" || add[3] == "-" || add[1] == "-" || add[2] == "-")
			return isValid;

		boolean isMatched = false;
		String[] addr = null;
		addr = U.getBingAddress(latLon[0], latLon[1]);
		if (addr != null) {
			isMatched = isMatchedLocation(add, addr);
			if (isMatched)
				return true;
		}
		addr = U.getGoogleAdd(latLon[0], latLon[1]);
		isMatched = isMatchedLocation(add, addr);
		if (isMatched)
			return true;

		return isValid;
	}

	public static String getCapitalise(String line) {
		line = line.trim().replaceAll("\\s+", " ");
		String[] words = line.trim().split(" ");
		String capital = null, wd;
		for (String word : words) {
			wd = word.substring(0, 1).toUpperCase() + word.substring(1);
			capital = (capital == null) ? wd : capital + " " + wd;
		}
		return capital;
	}

	public static String[] getBuilderList() {
		String path = "C://cache//builderlist.txt";
		int nline = 212;
		String[] textData = new String[nline];
		try {
			FileReader fr = new FileReader(path);
			BufferedReader br = new BufferedReader(fr);
			for (int i = 0; i < nline; i++) {
				textData[i] = br.readLine();
				// U.log(textData[i]);
			}
			br.close();
		} catch (Exception e) {
		}
		return textData;
	}

	public static String[] getAddressFromSec(String address, String skipLines) {

		String[] OUT = { "-", "-", "-", "-" };
		if (address != null) {
			String[] ADD = U.getZipStateCity(address);
			OUT[1] = ADD[0];
			OUT[2] = ADD[1];
			OUT[3] = ADD[2];
			String street = "-";
			// String ignore_ADD=;
			// U.log("ADD[0]::" + ADD[0]);
			if (ADD[0] != null || ADD[1] != null) {
				// U.log("ADDRESS : " + address);
				int streetSec = -1;
				String[] addSections = U.getValues(address, ">", "<");
				int counter = 0;
				if (addSections.length > 0) {
					for (String secLoc : addSections) {
						// U.log("***& " + secLoc);
						if (secLoc.contains("Rd") || !secLoc.contains("Draper") && secLoc.contains("Dr")
								|| secLoc.contains("Blvd") || secLoc.contains("Boulevard")) {
							counter++;
							// U.log("***& secLoc 1");
							continue;
						}
						String chkSec = Util.match(secLoc, ADD[1]);
						if (chkSec != null) {
							streetSec = counter;
							break;
						}
						// U.log("ADD[0]::" + ADD[0]);
						if (ADD[0] != null)
							chkSec = Util.match(secLoc, ADD[0]);
						if (chkSec != null) {
							streetSec = counter;
							break;
						}
						counter++;
					}
				}

				// U.log("@@@ " + streetSec);

				if (streetSec == 0)
					street = addSections[0];
				if (streetSec == 1)
					street = addSections[0];
				if (streetSec == 2) {
					String find = Util.match(addSections[0] + addSections[1], skipLines);
					if (find != null) {
						if (!addSections[0].contains(find))
							street = addSections[0];
						if (!addSections[1].contains(find))
							street = addSections[1];
					} else // if(find==null)
					{
						street = addSections[0] + ", " + addSections[1];
					}
				}
			}
			OUT[0] = street;
		}
		return OUT;
	}

	public static String[] getZipStateCity(String addSec) { // getZipStateCity
		String[] ZSC = { "-", "-", "-" };

		String state = getState(addSec);
		// U.log("state==" + state);
		String zip = getZip(addSec, state);
		// U.log("%%%%% :" + zip);
		if (zip != "-") {
			String ST = Util.match(zip, "\\w+");
			if (ST != "-") {
				String check = Util.match(state, ST);
				if (check == null) {
					zip = Util.match(zip, "\\w+\\W+(\\d{4,5})", 1);
					// U.log("*********" + zip);
					String Real = "\\w+\\W+" + zip;
					// U.log("Real*********" + Real);
					String stateReal = Util.match(addSec, Real);
					if (stateReal != null) {
						if (stateReal.contains("Hampshire") || stateReal.contains("Jersey")
								|| stateReal.contains("Mexico") || stateReal.contains("York")
								|| stateReal.contains("Carolina") || stateReal.contains("Dakota")
								|| stateReal.contains("Island") || stateReal.contains("Virginia")) {
							Real = "\\w+\\s\\w+\\W+" + zip;
							stateReal = Util.match(addSec, Real);
							state = Util.match(stateReal, "\\w+\\s\\w+");

						} else {
							state = Util.match(stateReal, "\\w+");

						}
					}
				}
			}
		}
		ZSC[1] = state;
		ZSC[2] = Util.match(zip, "\\d{4,5}");
		if (ZSC[2] == null)
			ZSC[2] = "-";
		String city = getCity(addSec, state);
		// U.log("********************@*" + city);
		String chkCity = city + "(.*?)<"; // \\s\\w{2}
		chkCity = Util.match(addSec, chkCity);
		if (chkCity != null) {
			String chkCity2 = chkCity.replace(city, "");
			if (chkCity2.contains("Rd") || chkCity2.contains("Dr") || chkCity2.contains("Blvd")
					|| chkCity2.contains("Boulevard") || chkCity2.contains("Park")) {
				String LOCaddSec = addSec.replace(chkCity, "");
				String LOCcity = getCity(LOCaddSec, state);
				U.log("POP**  :" + LOCcity);
				if (LOCcity.trim() != null && LOCcity.trim().length() > 3)
					city = LOCcity;
			}
		}
		ZSC[0] = city;
		// U.log(ZSC[0]+"::::::::::city");
		return ZSC;
	}

	public static String getStateAbbr(String state) {
		if (state.trim().contains("Alabama") || state.contains("AL"))
			return "AL";
		if (state.contains("Alaska") || state.contains("AK"))
			return "AK";
		if (state.contains("Arizona") || state.contains("AZ"))
			return "AZ";
		if (state.contains("Arkansas") || state.contains("AR"))
			return "AR";

		if (state.contains("California") || state.contains("CA"))
			return "CA";

		if (state.contains("Colorado") || state.contains("CO"))
			return "CO";

		if (state.contains("Connecticut") || state.contains("CT"))
			return "CT";
		if (state.contains("Delaware") || state.contains("DE"))
			return "DE";
		if (state.contains("Florida") || state.contains("FL"))
			return "FL";

		if (state.contains("Georgia") || state.contains("GA"))
			return "GA";
		if (state.contains("Hawaii") || state.contains("HI"))
			return "HI";
		if (state.contains("Idaho") || state.contains("ID"))
			return "ID";

		if (state.contains("Illinois") || state.contains("IL"))
			return "IL";
		if (state.contains("Indiana") || state.contains("IN"))
			return "IN";
		if (state.contains("Iowa") || state.contains("IA"))
			return "IA";
		if (state.contains("Kansas") || state.contains("KS"))
			return "KS";
		if (state.contains("Kentucky") || state.contains("KY"))
			return "KY";
		if (state.contains("Louisiana") || state.contains("LA"))
			return "LA";
		if (state.contains("Maine") || state.contains("ME"))
			return "ME";
		if (state.contains("Maryland") || state.contains("MD"))
			return "MD";
		if (state.contains("Massachusetts") || state.contains("MA"))
			return "MA";
		if (state.contains("Michigan") || state.contains("MI"))
			return "MI";
		if (state.contains("Minnesota") || state.contains("MN"))
			return "MN";
		if (state.contains("Mississippi") || state.contains("MS"))
			return "MS";
		if (state.contains("Missouri") || state.contains("MO"))
			return "MO";
		if (state.contains("Montana") || state.contains("MT"))
			return "MT";
		if (state.contains("Nebraska") || state.contains("NE"))
			return "NE";
		if (state.contains("Nevada") || state.contains("NV"))
			return "NV";
		if (state.contains("New Hampshire") || state.contains("NH"))
			return "NH";
		if (state.contains("New Jersey") || state.contains("NJ"))
			return "NJ";
		if (state.contains("New Mexico") || state.contains("NM"))
			return "NM";
		if (state.contains("New York") || state.contains("NY"))
			return "NY";
		if (state.contains("North Carolina") || state.contains("NC"))
			return "NC";
		if (state.contains("North Dakota") || state.contains("ND"))
			return "ND";
		if (state.contains("Ohio") || state.contains("OH"))
			return "OH";
		if (state.contains("Oklahoma") || state.contains("OK"))
			return "OK";
		if (state.contains("Oregon") || state.contains("OR"))
			return "OR";
		if (state.contains("Pennsylvania") || state.contains("PA"))
			return "PA";
		if (state.contains("Rhode Island") || state.contains("RI"))
			return "RI";
		if (state.contains("South Carolina") || state.contains("SC"))
			return "SC";
		if (state.contains("South Dakota") || state.contains("SD"))
			return "SD";
		if (state.contains("Tennessee") || state.contains("TN"))
			return "TN";
		if (state.contains("Texas") || state.contains("TX"))
			return "TX";
		if (state.contains("Utah") || state.contains("UT"))
			return "UT";
		if (state.contains("Vermont") || state.contains("VT"))
			return "VT";
		if (state.contains("Virginia") || state.contains("VA"))
			return "VA";
		if (state.contains("Washington") || state.contains("WA"))
			return "WA";
		if (state.contains("West Virginia") || state.contains("WV"))
			return "WV";
		if (state.contains("Wisconsin") || state.contains("WI"))
			return "WI";
		if (state.contains("Wyoming") || state.contains("WY"))
			return "WY";
		else
			return null;
	}

	private static String getState(String addSec) { // getZipStateCity

		String ignoreStreet = "1350 N New York St";
		addSec = addSec.replace(ignoreStreet, "");

		String state = Util.match(addSec,
				"Alabama|Alaska|Arizona|Arkansas|California|Colorado|Connecticut|Delaware|Florida|Georgia|Hawaii|Idaho|Illinois|Indiana|Iowa|Kansas|Kentucky|Louisiana|Maine|Maryland|Massachusetts|Michigan|Minnesota|Mississippi|Missouri|Montana|Nebraska|Nevada|New Hampshire|New Jersey|New Mexico|New York|North Carolina|North Dakota|Ohio|Oklahoma|Oregon|Pennsylvania|Rhode Island|South Carolina|South Dakota|Tennessee|Texas|Utah|Vermont|West Virginia|Virginia|Washington|Wisconsin|Wyoming");

		String[] sameName = { "Washington", "Maine", "Delaware", "Missouri", "Virginia", "Nevada", "Iowa", "Colorado" };
		// U.log("my state::"+state);
		if (state != null) {
			for (String same : sameName) {
				if (state.contains(same)) {
					addSec = addSec.replaceAll(same, "##@@");
					String LOLstate = getState(addSec);
					// U.log("LOLstate : " + LOLstate);
					if (LOLstate == null || LOLstate.length() < 3)
						state = same;
					else
						state = LOLstate;
					addSec = addSec.replace("##@@", same);
				}
			}
		}
		if (state == null) {
			state = Util.match(addSec, " [A-Z]{2} ");
		}
		if (state == null) {
			state = Util.match(addSec,
					"Alabama|Alaska|Arizona|Arkansas|California|Colorado|Connecticut|Delaware|Florida|Georgia|Hawaii|Idaho|Illinois|Indiana|Iowa|Kansas|Kentucky|Louisiana|Maine|Maryland|Massachusetts|Michigan|Minnesota|Mississippi|Missouri|Montana|Nebraska|Nevada|New Hampshire|New Jersey|New Mexico|New York|North Carolina|North Dakota|Ohio|Oklahoma|Oregon|Pennsylvania|Rhode Island|South Carolina|South Dakota|Tennessee|Texas|Utah|Vermont|West Virginia|Virginia|Washington|Wisconsin|Wyoming");
			if (state != null)
				return state;
		}

		if (state == null)
			state = "-";
		return state;
	}

	private static String getZip(String addSec, String state) { // getZipStateCity
		String match = state + "\\W+\\d{5}";
		String zip = Util.match(addSec, match);
		if (zip == null) {
			match = state + "\\W+\\d{4,5}";
			zip = Util.match(addSec, match);
		}
		if (zip == null) {
			zip = Util.match(addSec, "\\W+\\d{5}");
		}
		if (zip == null)
			zip = "-";
		return zip;
	}

	public static String getCity(String addSec, String state) { // getZipStateCity
		U.log("*#*  :" + state + ": *#*");
		if (state.length() > 2)
			state = USStates.abbr(state.trim());
		U.log(state);
		// addSec=addSec.replace("766 Austin Lane", "");
		String ingoreStreet = "2820 Bellevue Way NE|2303 San Marcos|Santa Rosa Villas|8541 N. Walnut Way|208 S. Auburn Heights Lane|2228 Tracy Place|3200 Osprey Lane|Kannapolis Parkway|1350 N New York St|8500 N. Fallbrook Avenue|Woodruff Road|Hopewell Road|Old Spartanburg Road|Zebulon Road|Kailua Road|Evans Mill Road|Arnold Overlook Lane|Wellington Woods Avenue|11405 Pacey's Pond Circle|405 San Luis Obispo St|4307 Walnut Avenue|12422 Mar Vista Street";
		addSec = addSec.replaceAll(ingoreStreet, "");
		String city = "-";
		if (state.trim().contains("Alabama") || state.contains("AL")) {
			city = Util.match(addSec,
					"Hunstville|Montevallo|Magnolia Springs|Orange Beach|Auburn|Alabaster|Albertville|Alexander City|Anniston|Athens|Atmore|Banks|Bessemer|Birmingham|Brewton|Center Point|Cottonton|Cullman|Daleville|Daphne|Decatur|Dothan|Enterprise|Eufaula|Fairfield|Fairhope|Florence|Foley|Forestdale|Fort Payne|Gadsden|Gardendale|Grove Hill|Hartselle|Helena|Homewood|Hoover|Hueytown|Huntsville|Jackson|Jasper|Kimberly|Leeds|Lisman|Lowndesboro|McCalla|Madison|Millbrook|Mobile|Monroeville|Montgomery|Mountain Brook|Muscle Shoals|Northport|Opelika|Oxford|Ozark|Pelham|Phenix City|Prattville|Prichard|Repton|Robertsdale|Saks|Saraland|Scottsboro|Selma|Sheffield|Slocomb|Smiths|Sylacauga|Talladega|Tallassee|Theodore|Tillmans Corner|Toney|Troy|Trussville|Tuscaloosa|Tuskegee|Vestavia([,\\W])?Hills|Warrior|Calera|Pinson|Chelsea|Springville|Moody|Fultondale|Odenville|Liberty Park Area|VestaviaHills|Hazel Green|Meridianville|Owens Cross Roads|Spanish Fort|Loxley|Semmes|Gulf Shores|Summerdale|Deatsville|Wetumpka|Vance|Moundville|Vestavia-Hills|Fulton|Harvest|Margaret");
		}
		if (state.contains("Alaska") || state.contains("AK")) {
			city = Util.match(addSec,
					"Anchorage|College|Fairbanks|Juneau|Kodiak|Adak|Akhiok|Akiachak|Akiak|Akutan|Alakanuk|Bettles|Bethel|Big Lake|Bristol Bay|Coffman Cove|Cordova|Crow Village|Delta Junction|Dillingham|Ester|Fairbanks|Haines|Homer|Hoonah|Kenai|Ketchikan|Knik|Kotzebue|Matanuska-Susitna Borough|Nome|North Pole|Palmer|Pelican|Petsburg|Seward|Sitka|Skagway|Soldotna|Thorne Bay|Unalaska|Valdez|Wasilla|Whittier|Wrangell");
		}
		if (state.contains("Arizona") || state.contains("AZ")) {
			city = Util.match(addSec,
					"Barcelona|Apache Junction|Avondale|Bullhead City|Casa Grande|Casas Adobes|Catalina Foothills|Cave Creek|Chambers|Chandler|Chinle|Cottonwood-Verde Village|Douglas|Drexel Heights|Eloy|Flagstaff|Florence|Flowing Wells|Fortuna Foothills|Fountain Hills|Fredonia|Gilbert|Glendale|Globe|Goodyear|Green Valley|Heber|Holbrook|Houck|Lake Havasu City|Marana|Mayer|Mesa|Mohave Valley|New Kingman-Butler|New River|Nogales|Oro Valley|Paradise Valley|Payson|Peoria|Phoenix|Pima|Prescott Valley|Prescott|Quartzsite|Rimrock|Riviera|Sacaton|Safford|San Luis|Scottsdale|Sedona|Show Low|Sierra Vista Southeast|Sierra Vista|Sun City West|Sun City|Sun Lakes|Surprise|Tacna|Tanque Verde|Teec Nos Pos|Tempe|Tonalea|Tucson|Whiteriver|Willcox|San Tan Valley|Maricopa|Buckeye|Queen Creek|Sahuarita|Vail|Yuma|Page");
		}
		if (state.contains("Arkansas") || state.contains("AR")) {
			city = Util.match(addSec,
					"Amity|Arkadelphia|Atkins|Batesville|Bella Vista|Bentonville|Benton|Blytheville|Cabot|Camden|Conway|Dermott|El Dorado|Fayetteville|Forrest City|Fort Smith|Franklin|Hampton|Hardy|Harrison|Heber Springs|Hermitage|Hope|Hot Springs|Jonesboro|Little Rock|Magnolia|Malvern|Marmaduke|Marshall|Maumelle|Mountain Home|Mountain View|Newport|Norfork|North Little Rock|Oden|Ola|Paragould|Pine Bluff|Pocahontas|Rogers|Russellville|Salem|Searcy|Sherwood|Shirley|Siloam Springs|Sparkman|Springdale|Texarkana|Van Buren|Waldron|West Memphis|Wynne");
		}

		if (state.contains("California") || state.contains("CA")) {
			city = Util.match(addSec,
					"Arabella|Winchester|Diablo Grande|Rolling Hills Estates|Adelanto|Agoura Hills|Alameda|Alamo|Albany|Alhambra|Aliso Viejo|Alpine|Altadena|Alta|Alum Rock|Anaheim Hills|Anaheim|Angels Camp|Angwin|Antioch|Apple Valley|Aptos|Arcadia|Arcata|Arden-Arcade|Arroyo Grande|Artesia|Arvin|Ashland|Atascadero|Atwater|Auburn|Avenal|Avocado Heights|Azusa|Bakersfield|Baker|Baldwin Park|Banning|Barstow|Bass Lake|Bay Point|Baywood-Los Osos|Beaumont|Bell Gardens|Bellflower|Belmont|Benicia|Berkeley|Beverly Hills|Big Bear City|Big Creek|Blackhawk-Camino Tassajara|Bloomington|Blythe|Bonita|Bostonia|Brawley|Brea|Brentwood|Buena Park|Burbank|Burlingame|Cabazon|Calabasas|Calexico|Camarillo|Cameron Park|Campbell|Canoga Park|Capitola|Carlsbad|Carmel|Carmichael|Carpinteria|Carson|Casa de Oro-Mount Helix|Castro Valley|Cathedral City|Central Valley|Ceres|Cerritos|Chatsworth|Cherryland|Chico|Chino Hills|Chino|Chowchilla|Chula Vista|Citrus Heights|Citrus|Claremont|Clayton|Clearlake|Cloverdale|Clovis|Coachella|Coalinga|Colton|Commerce|Compton|Concord|Corcoran|Corning|Coronado|Corona|Costa Mesa|Cotati|Coto de Caza|Covina|Crescent City|Crestline|Cudahy|Culver City|Cupertino|Cypress|Daly City|Dana Point|Danville|Delano|Desert Hot Springs|Diamond Bar|Dinuba|Dixon|Downey|Duarte|Dublin|East Hemet|East Los Angeles|East Palo Alto|East San Gabriel|El Cajon|El Centro|El Cerrito|El Dorado Hills|El Monte|El Segundo|El Sobrante|Elk Grove|Encinitas|Encino|Escondido|Esparto|Eureka|Fair Oaks|Fairfield|Fallbrook|Fillmore|Florence-Graham|Florin|Folsom|Fontana|Foothill Farms|Foothill Ranch|Fort Bragg|Fortuna|Foster City|Fountain Valley|Fremont|Fresno|Fullerton|Galt|Garden Grove|Gardena|Gilroy|Glen Avon|Glendale|Glendora|Goleta|Grand Terrace|Granite Bay|Grass Valley|Greenfield|Grover Beach|Gualala|Guerneville|Hacienda Heights|Half Moon Bay|Hanford|Hawaiian Gardens|Hawthorne|Hayward|Healdsburg|Hemet|Hercules|Hermosa Beach|Hesperia|Highland|Hillsborough|Hollister|Hollywood|Homewood|Huntington Beach|Huntington Park|Imperial Beach|Indio|Inglewood|Irvine|Isla Vista|Jackson|Janesville|Junction City|Keene|King City|La Canada-Flintridge|La Crescenta-Montrose|La Habra|La Jolla|La Mesa|La Mirada|La Palma|La Presa|La Puente|La Quinta|La Riviera|La Verne|Lafayette|Laguna Beach|Laguna Hills|Laguna Niguel|Laguna Woods|Laguna|Lake Elsinore|Lake Forest|Lake Los Angeles|Lakehead|Lakeside|Lakewood|Lamont|Lancaster|Larkspur|Lathrop|Lawndale|Laytonville|Lemon Grove|Lemoore|Lennox|Lincoln|Linda|Lindsay|Live Oak|Livermore|Livingston|Lodi|Loma Linda|Lomita|Lompoc|Long Beach|Los Alamitos|Los Altos|Los Angeles|Los Banos|Los Gatos|Lynwood|Madera|Magalia|Malibu|Manhattan Beach|Manteca|Marina Del Rey|Marina|Martinez|Marysville|Maywood|McKinleyville|Menlo Park|Merced|Mill Valley|Millbrae|Milpitas|Mineral|Mira Loma|Mission Viejo|Modesto|Monrovia|Montclair|Montebello|Montecito|Monterey Park|Monterey|Moorpark|Moraga|Moreno Valley|Morgan Hill|Morro Bay|Mount Aukum|Mountain View|Murrieta|Napa|National City|Newark|Newberry Springs|Newport Beach|Nicolaus|Nipomo|Norco|North Auburn|North Fair Oaks|North Highlands|North Hollywood|Northridge|Norwalk|Novato|Oakdale|Oakland|Oakley|Oceanside|Oildale|Olivehurst|Ontario|Orangevale|Orange|Orcutt|Orinda|Orleans|Oroville|Oxnard|Pacific Grove|Pacifica|Pacoima|Palm Desert|Palm Springs|Palmdale|Palo Alto|Palos Verdes Estates|Panorama City|Paradise|Paradise, NVParamount|Parkway-South Sacramento|Parlier|Pasadena|Paso Robles|Patterson|Pedley|Perris|Petaluma|Philo|Pico Rivera|Piedmont|Pine Valley|Pinecrest|Pinole|Pioneer|Pittsburg|Placentia|Placerville|Playa Del Rey|Pleasant Hill|Pleasanton|Pomona|Port Hueneme|Porterville|Portola|Poway|Prunedale|Ramona|Rancho Cordova|Rancho Cucamonga|Rancho Mirage|Rancho Palos Verdes|Rancho San Diego|Rancho Santa Margarita|Red Bluff|Redding|Redlands|Redondo Beach|Redwood City|Redwood Estates|Reedley|Rialto|Richmond|Ridgecrest|Rio Linda|Ripon|Riverbank|Riverdale|Riverside|Rocklin|Rodeo|Rohnert Park|Rosamond|Rosemead|Rosemont|Roseville|Rossmoor|Rowland Heights|Rubidoux|Sacramento|Salida|Salinas|San Andreas|San Anselmo|San Bernardino|San Bruno|San Carlos|San Clemente|San Diego|San Dimas|San Fernando|San Francisco|San Gabriel|San Jacinto|San Jose|San Juan Capistrano|San Leandro|San Lorenzo|San Luis Obispo|San Marcos|San Marino|San Mateo|San Pablo|San Pedro|San Quentin|San Rafael|San Ramon|San Ysidro|Sanger|Santa Ana|Santa Barbara|Santa Clara|Santa Clarita|Santa Cruz|Santa Fe Springs|Santa Maria|Santa Monica|Santa Paula|Santa Rosa|Santee|Saratoga,|Sausalito|Scotts Valley|Seal Beach|Seaside|Sebastopol|Selma|Shafter|Sherman Oaks|Sierra Madre|Simi Valley|Solana Beach|Soledad|Sonora|South El Monte|South Gate|South Lake Tahoe|South Pasadena|South San Francisco|South San Jose Hills|South Whittier|South Yuba City|Spring Valley|Springville|Stanford|Stanton|Stockton|Suisun City|Sun City|Sun Valley|Sunnyvale|Susanville|Sutter Creek|Sylmar|Tamalpais-Homestead Valley|Tecate|Tehachapi|Temecula|Temple City|Thousand Oaks|Torrance|Tracy|Truckee|Tulare|Turlock|Tustin Foothills|Tustin|Twentynine Palms|Twin Bridges|Ukiah|Union City|Upland|Upper Lake|Vacaville|Valencia|Valinda|Valle Vista|Vallecito|Vallejo|Valley Springs|Van Nuys|Venice|Ventura|Victorville|View Park-Windsor Hills|Vincent|Vineyard|Visalia|Vista|Walnut Creek|Walnut Park|Walnut|Warner Springs|Wasco|Watsonville|West Carson|West Covina|West Hollywood|West Puente Valley|West Sacramento|West Whittier-Los Nietos|Westminster|Westmont|Whittier|Wildomar|Willits|Willowbrook|Wilmington|Windsor|Winter Gardens|Woodland Hills|Woodland|Yorba Linda|Yuba City|Yucaipa|Yucca Valley|Eastvale|Calimesa|Jurupa Valley|Menifee");
		}
		if (state.contains("Colorado") || state.contains("CO")) {
			city = Util.match(addSec,
					"Severance|Fort Lupton|Clifton|Aguilar|Alamosa|Arvada|Aspen|Aurora|Berkley|Black Forest|Boulder|Brighton|Broomfield|Buena Vista|Canon City|Castle Rock|Castlewood|Cimarron Hills|Colorado Springs|Columbine|Commerce City|Craig|Crested Butte|Crowley|Denver|Dillon|Dinosaur|Durango|Eads|Englewood|Estes Park|Fairplay|Federal Heights|Fort Carson|Fort Collins|Fort Morgan|Fountain|Glenwood Springs|Golden|Grand Junction|Greeley|Greenwood Village|Gunnison|Highlands Ranch|Hugo|Ken Caryl|Lafayette|Lakewood|Laporte|Leadville|Littleton|Longmont|Louisville|Loveland|Monte Vista|Montrose|Northglenn|Pagosa Springs|Parker|Pine|Pueblo West|Pueblo|Salida|San Luis|Security-Widefield|Sherrelwood|Southglenn|Steamboat Springs|Sterling|Thornton|Trinidad|U S A F Academy|Vail|Walden|Walsenburg|Welby|Westminster|Wheat Ridge|Windsor|Woodland Park|Centennial|Elizabeth|Firestone|Frederick|Erie|Timnath|Wellington|Lochbuie");
		}
		if (state.contains("Connecticut") || state.contains("CT")) {
			city = Util.match(addSec,
					"Ansonia|Avon|Berlin|Bethel|Bloomfield|Branford|Bridgeport|Bristol|Brookfield|Central Manchester|Cheshire|Clinton|Colchester|Conning Towers-Nautilus Park|Coventry|Cromwell|Danbury|Darien|Derby|East Hampton|East Hartford|East Haven|East Lyme|Ellington|Enfield|Fairfield|Farmington|Gales Ferry|Glastonbury|Granby|Greenwich|Griswold|Groton|Guilford|Hamden|Hartford|Killingly|Ledyard|Madison|Manchester|Mansfield|Meriden|Middletown|Milford|Monroe|Montville|Mystic|Naugatuck|New Britain|New Canaan|New Fairfield|New Haven|New London|New Milford|Newington|Newtown|North Branford|North Haven|Norwalk|Norwich|Old Saybrook|Orange|Plainfield|Plainville|Plymouth|Ridgefield|Rocky Hill|Seymour|Shelton|Simsbury|Somers|South Windsor|Southbury|Southington|Stafford|Stamford|Stonington|Storrs|Stratford|Suffield|Thomaston|Tolland|Torrington|Trumbull|Unionville|Vernon|Wallingford Center|Wallingford|Washington Depot|Waterbury|Waterford|Watertown|West Hartford|West Haven|Weston|Westport|Wethersfield|Willimantic|Wilton|Winchester|Windham|Windsor Locks|Windsor|Winsted|Wolcott");
		}
		if (state.contains("Delaware") || state.contains("DE")) {
			city = Util.match(addSec,
					"Magnolia|Frederica|Milton|Smyrna|Milford|Dagsboro|Ocean View|Frankford|Bear|Brookside|Dover|Glasgow|Hockessin|New Castle|Newark|Pike Creek|Selbyville|Wilmington|Townsend|Middletown|Fenwick Island");
		}
		if (state.contains("Florida") || state.contains("FL")) {
			city = Util.match(addSec,
					"Seffner|Wildwood|Port St. Joe|Southport|Mulberry|Mt. Dora|Debary|Valrico|Mims|Apollo Beach|Palm Shores|Alachua|Altamonte Springs|Apopka|Atlantic Beach|Auburndale|Aventura|Azalea Park|Bartow|Bayonet Point|Bayshore Gardens|Bellair-Meadowbrook Terrace|Belle Glade|Bellview|Bloomingdale|Boca Del Mar|Boca Raton|Bonita Springs|Boynton Beach|Bradenton Beach|Bradenton|Brandon|Brent|Brooksville|Brownsville|Bunnell|Callaway|Canal Point|Cape Coral|Carol City|Casselberry|Citrus Park|Citrus Ridge|Clearwater|Cocoa Beach|Cocoa|Coconut Creek|Conway|Cooper City|Coral Gables|Coral Springs|Coral Terrace|Country Club|Country Walk|Crestview|Cutler Ridge|Cutler|Cypress Lake|Dade City|Dania Beach|Davie|Daytona Beach|De Bary|De Funiak Springs|De Land|Deerfield Beach|Delray Beach|Deltona|Doral|Dunedin|East Lake|Edgewater|Egypt Lake-Leto|Elfers|Englewood|Ensley|Eustis|Fairview Shores|Fernandina Beach|Ferry Pass|Flagler Beach|Florida Ridge|Forest City|Fort Lauderdale|Fort Myers Beach|Fort Myers|Fort Pierce|Fleming Island|Fort Walton Beach|Fountainbleau|Fruit Cove|Fruitville|Gainesville|Gladeview|Glenvar Heights|Golden Gate|Golden Glades|Gonzalez|Greater Carrollwood|Greater Northdale|Greater Sun Center|Greenacres|Gulf Gate Estates|Gulfport|Haines City|Hallandale|Hamptons at Boca Raton|Hialeah Gardens|Hialeah|Hobe Sound|Holiday|Holly Hill|Hollywood|Homestead|Homosassa Springs|Orlando|Hosford|Hudson|Immokalee|Interlachen|Inverness|Iona|Ives Estates|Jacksonville Beach|Jacksonville|Jasmine Estates|Jensen Beach|Jupiter|Kendale Lakes|Kendall West|Kendall|Key Biscayne|Key Largo|Key West|Keystone|Kings Point|Kissimmee|Lady Lake|Lake Butler|Lake City|Lake Magdalene|Lake Mary|Lake Wales|Lake Worth Corridor|Lake Worth|Lakeland Highlands|Lakeland|Lakewood Park|Land O' Lakes|Largo|Lauderdale Lakes|Lauderhill|Leesburg|Lehigh Acres|Leisure City|Lighthouse Point|Lockhart|Longwood|Lutz|Lynn Haven|Maitland|Marathon|Marco Island|Marco|Margate|Mary Esther|Meadow Woods|Melbourne|Merritt Island|Miami Beach|Miami Lakes|Miami Shores|Miami Springs|Miami Gardens|Miami|Middleburg|Milton|Miramar|Montverde|Mount Dora|Myrtle Grove|Naples|New Port Richey|New Smyrna Beach|Niceville|Nokomis|Norland|North Fort Myers|North Lauderdale|North Miami Beach|North Miami|North Palm Beach|North Port|Oak Ridge|Oakland Park|Ocala|Ocoee|Ojus|Okeechobee|Oldsmar|Olympia Heights|Opa-Locka|Orange City|Orange Park|Ormond Beach|Oviedo|Palatka|Palm Bay|Palm Beach Gardens|Palm Beach|Palm City|Palm Coast|Palm Harbor|Palm River-Clair Mel|Palm Springs|Palm Valley|Palmetto Estates|Palmetto|Panama City|Parkland|Pembroke Pines|Pensacola|Pine Hills|Pinecrest|Pinellas Park|Pinewood|Placida|Plant City|Poinciana|Pompano Beach|Port Charlotte|Port Orange|Port Richey|Port Saint Joe|Port Salerno|Port St. John|Port St. Lucie|Princeton|Punta Gorda|Quincy|Richmond West|Riverview|Riviera Beach|Rockledge|Royal Palm Beach|Ruskin|Safety Harbor|San Carlos Park|Sandalfoot Cove|Sanderson|Sanford|Sarasota Springs|Sarasota|Scott Lake|Sebastian|Sebring|Seminole|South Bradenton|South Daytona|South Miami Heights|South Miami|South Venice|Spring Hill|Stuart|St. Augustine|St. Cloud|St. Petersburg|Summerland Key|Sunny Isles Beach|Sunrise|Sweetwater|Tallahassee|Tamarac|Tamiami|Tampa|Tarpon Springs|Temple Terrace|The Crossings|The Hammocks|Titusville|Town 'n' Country|Union Park|University Park|University|Upper Grand Lagoon|Venice|Vero Beach South|Vero Beach|Villas|Warrington|Wekiwa Springs|Wellington|West and East Lealman|West Little River|West Palm Beach|West Pensacola|Westchase|Westchester|Weston|Westwood Lakes|Wewahitchka|Wilton Manors|Winter Garden|Winter Haven|Winter Park|Winter Springs|Wright|Yulee|Yeehaw Junction|Zephyrhills|Windermere|Grand Island|Saint Cloud|Tavares|Lake Alfred|Davenport|Harmony|Clermont|Deland|Groveland|Lake Helen|West Melbourne|St. Johns|Callahan|Green Cove Springs|Saint Augustine|Beverly Beach|Freeport|Inlet Beach|Santa Rosa Beach|Panama City Beach|Point Washington|Pace|Navarre|Cantonment|Gulf Breeze|Miami Dade County|Haverhill|Port St Lucie|Osprey|Sun City Center|Thonotosassa|Trinity|Wimauma|Gibsonton|Wesley Chapel|Ellenton|Land O Lakes|St Petersburg|Viera|Jacksonville|Lakeside|Plantation|Alva,|Point Washington");
		}
		if (state.contains("Georgia") || state.contains("GA")) {
			city = Util.match(addSec,
					"Villa Rica|Lithia Springs|Bishop|Senoia|Stockbridge|Grayson|Austell|Bethlehem|Rex|Loganville|Grovetown|Hephzibah|Acworth|Albany|Alpharetta|Alto|Americus|Athens|Atlanta,|Augusta|Bainbridge|Belvedere Park|Brunswick|Buford|Calhoun|Candler-McAfee|Carrollton|Cartersville|Cochran|College Park|Columbus|Commerce|Conyers|Cordele|Covington|Dahlonega|Dalton|Dawson|Decatur|Demorest|Douglasville|Douglas|Druid Hills|Dublin|Duluth|Dunwoody|Dacula|East Point|Elberton|Evans|Fayetteville|Forest Park|Fort Benning South|Fort Stewart|Gainesville|Garden City|Georgetown|Griffin|Hinesville|Jonesboro|Kennesaw|Kingsland|La Grange|Lawrenceville|Lilburn|Lithonia|Mableton|Macon|Marietta|Martinez|Milledgeville|Monroe|Morrow|Moultrie|Mountain Park|Newnan|Newton|Norcross|North Atlanta|North Decatur|North Druid Hills|Panthersville|Peachtree City|Powder Springs|Redan|Reidsville|Riverdale|Rome|Roswell|Sandy Springs|Savannah|Smyrna|Snellville|Statesboro|Stone Mountain|St. Marys|St. Simons|Sugar Hill|Thomasville|Tifton|Toccoa,|Tucker|Union City|Union Point|Valdosta|Vidalia|Warner Robins|Waycross|Wilmington Island|Winder|Woodstock|Cumming|Hampton|Ellenwood|Hiram|Mcdonough|Dallas|Locust Grove|Fairburn|Flowery Branch|Canton|Suwanee|Byron|Kathleen|Perry|Guyton|Pooler|Richmond Hill");
		}
		if (state.contains("Hawaii") || state.contains("HI")) {
			city = Util.match(addSec,
					"Aiea|Ewa Beach|Halawa|Hanalei|Hilo|Honolulu|Kahului|Kailua Kona|Kailua|Kaneohe Station|Kaneohe|Kihei|Lahaina|Laupahoehoe|Lihue|Makakilo City|Makawao|Mililani Town|Nanakuli|Pearl City|Schofield Barracks|Wahiawa|Waianae|Wailuku|Waimalu|Waipahu|Waipio|Poipu - Koloa|Kapolei|Makakilo|Kamuela|Kona/ Kohala Coast");
		}
		if (state.contains("Idaho") || state.contains("ID")) {
			city = Util.match(addSec,
					"Ashton|Blackfoot|Boise|Caldwell|Cambridge|Coeur d'Alene|Cottonwood|Culdesac|Dubois|Eagle|Emmett|Fairfield|Garden City|Grace|Grangeville|Harrison|Idaho City|Idaho Falls|Lewiston|Malad City|Meridian|Moore|Moscow|Mountain Home|Nampa|North Fork|Paris|Pocatello|Post Falls|Rexburg|Rupert|Sandpoint|Shoshone|Sun Valley|Twin Falls|Wallace");
		}
		if (state.contains("Illinois") || state.contains("IL")) {
			city = Util.match(addSec,
					"Antioch|Yorkville|Addison|Algonquin|Alsip|Alton|Arlington Heights|Aurora|Barrington|Bartlett|Batavia|Beach Park|Beecher City|Belleville|Bellwood|Belvidere|Bensenville|Berwyn|Blandinsville|Bloomingdale|Bloomington|Blue Island|Bolingbrook|Bourbonnais|Bradley|Bridgeview|Brimfield|Brookfield|Buffalo Grove|Burbank|Burr Ridge|Cahokia|Calumet City|Canton|Carbondale|Carol Stream|Carpentersville|Cary|Centralia|Champaign|Charleston|Chicago Heights|Chicago Ridge|Chicago|Cicero|Collinsville|Country Club Hills|Crest Hill|Crestwood|Crystal Lake|Danville|Darien|Decatur|Deerfield|DeKalb|Des Plaines|Dixon|Dolton|Downers Grove|East Moline|East Peoria|East Saint Louis|East St. Louis|Edwardsville|Effingham|Elgin|Elk Grove Village|Elmhurst|Elmwood Park|Evanston|Evergreen Park|Fairview Heights|Flossmoor|Forest Park|Frankfort|Franklin Park|Freeport|Gages Lake|Galesburg|Geneva|Glen Carbon|Glen Ellyn|Glendale Heights|Glenview|Godfrey|Goodings Grove|Granite City|Grayslake|Gurnee|Hanover Park|Harvey|Hazel Crest|Herrin|Hickory Hills|Highland Park|Hillside|Hinsdale|Hoffman Estates|Homewood|Jacksonville|Joliet|Justice|Kankakee|Kewanee|La Grange Park|La Grange|Lake Forest|Lake in the Hills|Lake Zurich|Lansing|Lemont|Libertyville|Lincolnwood|Lincoln|Lindenhurst|Lisle|Lockport|Lombard|Loves Park|Lyons|Machesney Park|Macomb|Marion|Markham|Mascoutah|Matteson|Mattoon|Maywood|McHenry|Melrose Park|Midlothian|Mokena|Moline|Morris|Morton Grove|Morton|Mount Prospect|Mount Vernon|Mundelein|Murphysboro|Naperville|New Lenox|Niles|Normal|Norridge|North Aurora|North Chicago|Northbrook|Northlake|O'Fallon|Oak Forest|Oak Lawn|Oak Park|Orland Park|Oswego|Ottawa|Palatine|Palos Heights|Palos Hills|Palos Park|Park Forest|Park Ridge|Pekin|Peoria|Petersburg|Plainfield|Pontiac|Prairie Du Rocher|Prospect Heights|Quincy|Rantoul|Richton Park|River Forest|River Grove|Riverdale|Rock Island|Rockford|Rolling Meadows|Romeoville|Roselle|Round Lake Beach|Sauk Village|Schaumburg|Schiller Park|Skokie|South Elgin|South Holland|Springfield|Sterling|Streamwood|Streator|St. Charles|Summit|Swansea|Sycamore|Taylorville|Tinley Park|Urbana|Vernon Hills|Villa Park|Warrenville|Washington|Waukegan|West Chicago|Westchester|Western Springs|Westmont|Wheaton|Wheeling|Wilmette|Winnetka|Wood Dale|Wood River|Woodfield-Schaumburg|Woodridge|Woodstock|Worth|Zion|Pingree Grove|Volo|Huntley|Glencoe|Woodridge");
		}
		if (state.contains("Indiana") || state.contains("IN")) {
			city = Util.match(addSec,
					"Schereville|Anderson|Auburn|Bedford|Beech Grove|Birdseye|Bloomington|Brownsburg|Carmel|Chesterton|Clarksville|Columbus|Connersville|Crawfordsville|Crown Point|Dyer|Earl Park|East Chicago|Elizabethtown|Elkhart|Evansville|Fishers|Fort Wayne|Fowler|Frankfort|Franklin|Gary|Goshen|Granger|Greenfield|Greensburg|Greenwood|Griffith|Hammond|Highland|Hobart|Huntington|Indianapolis|Jasper|Jeffersonville|Kokomo|La Porte|Lafayette|Lake Station|Lawrence|Lebanon|Logansport|Lowell|Madison|Marion|Martinsville|Merrillville|Michigan City|Mishawaka|Muncie|Munster|New Albany|New Castle|New Haven|Newburgh|Noblesville|Peru|Plainfield|Portage|Portland|Princeton|Richmond|Schererville|Seymour|Shelbyville|South Bend|Speedway|Sullivan|Terre Haute|Valparaiso|Vincennes|Wabash|Warsaw|Washington|West Lafayette");
		}
		if (state.contains("Iowa") || state.contains("IA")) {
			city = Util.match(addSec,
					"Alden|Altoona|Amana|Ames|Ankeny|Bettendorf|Boone|Burlington|Carroll|Cedar Falls|Cedar Rapids|Charles City|Churdan|Clinton|Clive|Collins|Coralville|Council Bluffs|Davenport|Davis City|Des Moines|Dubuque|Fort Dodge|Fort Madison|Grinnell|Indianola|Iowa City|Keokuk|Logan|Manning|Marion|Marshalltown|Mason City|Mingo|Montezuma|Muscatine|New Hampton|Newton|Ocheyedan|Oskaloosa|Ottumwa|Red Oak|Sheldon|Shenandoah|Shenandoah|Sioux City|Spencer|Storm Lake|Thurman|Underwood|Union|Urbandale|Waterloo|West Des Moines");
		}
		if (state.contains("Kansas") || state.contains("KS")) {
			city = Util.match(addSec,
					"Alma|Arkansas City|Atchison|Beloit|Caldwell|Coffeyville|De Soto|Derby|Dodge City|El Dorado|Emporia|Enterprise|Garden City|Gardner|Great Bend|Hays|Hiawatha|Hutchinson|Junction City|Kansas City|Kingman|Lawrence|Leavenworth|Leawood|Lenexa|Liberal|Madison|Manhattan|Marysville|McPherson|Merriam|Newton|Olathe|Ottawa|Overland Park|Parsons|Phillipsburg|Pittsburg|Prairie Village|Salina|Shawnee|Topeka|Westmoreland|Wichita|Winfield");
		}
		if (state.contains("Kentucky") || state.contains("KY")) {
			city = Util.match(addSec,
					"Ashland|Barbourville|Bardstown|Berea|Bowling Green|Burlington|Burnside|Campbellsville|Carrollton|Covington|Danville|Elizabethtown|Erlanger|Fern Creek|Florence|Fort Campbell North|Fort Knox|Fort Thomas|Frankfort|Georgetown|Glasgow|Harlan|Henderson|Highview|Hopkinsville|Independence|Jeffersontown|La Grange|Lancaster|Lexington|London|Louisa|Louisville|Madisonville|Mayfield|Middlesborough|Murray|Newburg|Newport|Nicholasville|Okolona|Owensboro|Paducah|Pikeville|Pleasure Ridge Park|Radcliff|Richmond|Shelbyville|Shively|Somerset|Stanton|St. Matthews|Valley Station|Winchester");
		}
		if (state.contains("Louisiana") || state.contains("LA")) {
			city = Util.match(addSec,
					"Rayne|Abbeville|Alexandria|Angie|Baker|Bastrop|Baton Rouge|Bayou Cane|Bogalusa|Bossier City|Boyce|Chalmette|Convent|Covington|Crowley|Denham Springs|Destrehan|Estelle|Eunice|Fort Polk South|Gonzales|Gretna|Hammond|Harvey|Houma|Jefferson|Jennings|Kenner|La Place|Lafayette|Lake Charles|Leesville|Logansport|Luling|Mandeville|Marrero|Meraux|Merrydale|Metairie|Minden|Monroe|Morgan City|Moss Bluff|Natchitoches|New Iberia|New Orleans|Opelousas|Pineville|Plaquemine|Raceland|Rayville|Reserve|River Ridge|Ruston|Shreveport|Slidell|Sulphur|Tallulah|Terrytown|Thibodaux|Timberlane|West Monroe|Westwego|Woodmere|Zachary|From Baton Rouge|Addis|Prairieville|Watson|Madisonville|Ponchatoula|Robert|Duson|Carencro|Youngsville");
		}
		if (state.contains("Maine") || state.contains("ME")) {
			city = Util.match(addSec,
					"Ashland|Auburn|Augusta|Bangor|Biddeford|Brunswick|Castine|Danforth|Falmouth|Freeport|Gorham|Greenville|Guilford|Jay|Kennebunk|Lewiston|Limestone|Machias|Orono|Portland|Princeton|Saco|Sanford|Scarborough|South Portland|Southwest Harbor|Stonington|Surry|Waterville|Westbrook|Windham|York");
		}
		if (state.contains("Maryland") || state.contains("MD")) {
			city = Util.match(addSec,
					"Accokeek|Edgewater|White Plains|Ijamsville|Marriottsville|Sykesville|Clarksburg|Laytonsville|Clarksville|Frankford|Millersville|Aberdeen Proving Ground|Aberdeen|Adelphi|Annapolis|Arbutus|Arnold|Aspen Hill|Ballenger Creek|Baltimore|Bel Air North|Bel Air South|Bel Air|Beltsville|Bethesda|Bowie|Brooklyn Park|Calverton|Cambridge|Camp Springs|Carney|Catonsville|Chesapeake Ranch Estates-Drum Point|Chillum|Church Hill|Clinton|Cockeysville|Colesville|College Park|Columbia|Coral Hills|Crofton|Cumberland|Damascus|Dundalk|East Riverdale|Easton|Edgewood|Eldersburg|Elkridge|Elkton|Ellicott City|Essex|Fairland|Ferndale|Forestville|Fort Washington|Frederick|Fulton|Friendly|Gaithersburg|Germantown|Glen Burnie|Glenn Dale|Greater Landover|Greater Upper Marlboro|Green Haven|Green Valley|Greenbelt|Hagerstown|Halfway|Havre de Grace|Hillcrest Heights|Hyattsville|Joppatowne|Kettering|Lake Shore|Langley Park|Lanham-Seabrook|Lansdowne-Baltimore Highlands|Laurel|Lexington Park|Linganore-Bartonsville|Lochearn|Lutherville-Timonium|Manchester|Mays Chapel|Middle River|Milford Mill|Montgomery Village|New Carrollton|North Bethesda|North Laurel|North Potomac|Ocean Pines|Odenton|Olney|Overlea|Owings Mills|Oxon Hill-Glassmanor|Parkville|Parole|Pasadena|Perry Hall|Pikesville|Potomac|Randallstown|Redland|Reisterstown|Ridgely|Riviera Beach|Rockville|Rosaryville|Rosedale|Rossville|Salisbury|Savage-Guilford|Severna Park|Severn|Silver Spring|South Gate|South Laurel|St. Charles|Suitland-Silver Hill|Takoma Park|Towson|Upper Marlboro|Waldorf|Walker Mill|Westernport|Westminster|Wheaton-Glenmont|White Oak|Woodlawn|Woodstock|New Market|Brandywine|Glenarden|Mitchellville|Cooksville|Hanover|Marriottsville");

		}
		if (state.contains("Massachusetts") || state.contains("MA")) {
			city = Util.match(addSec,
					"Abington|Acton|Acushnet|Agawam|Amesbury|Amherst Center|Amherst|Andover|Arlington|Ashburnham|Ashland|Athol|Attleboro|Auburn|Ayer|Barnstable Town|Bedford|Belchertown|Bellingham|Belmont|Beverly|Billerica|Boston|Bourne|Braintree|Brewster|Bridgewater|Brockton|Brookline|Burlington|Buzzards Bay|Cambridge|Canton|Carver|Centerville|Charlemont|Charlton|Chelmsford|Chelsea|Chicopee|Clinton|Concord|Dalton|Danvers|Dartmouth|Dedham|Dennis|Dracut|Dudley|Duxbury|East Bridgewater|East Longmeadow|Easthampton|Easton|Everett|Fairhaven|Fall River|Falmouth|Fitchburg|Foxborough|Framingham|Franklin|Gardner|Gloucester|Grafton|Greenfield|Groton|Hanover|Harwich|Haverhill|Hingham|Holbrook|Holden|Holliston|Holyoke|Hopkinton|Hudson|Hull|Ipswich|John Fitzgerald Kennedy|Kingston|Lawrence|Lee|Leicester|Leominster|Lexington|Longmeadow|Lowell|Ludlow|Lynnfield|Lynn|Malden|Mansfield|Marblehead|Marlborough|Marshfield|Mashpee|Maynard|Medfield|Medford|Medway|Melrose|Methuen|Middlesex Essex Gmf|Milford|Millbury|Milton|Nantucket|Natick|Needham|New Bedford|Newburyport|Newtonville|Newton|Norfolk|North Adams|North Andover|North Attleborough Center|North Dighton|North Easton|North Falmouth|North Reading|Northampton|Northborough|Northbridge|Northfield|Norton|Norwood|Orange|Oxford|Palmer|Peabody|Pembroke|Pepperell|Pittsfield|Plymouth|Quincy|Randolph|Raynham|Reading|Rehoboth|Revere|Rockland|Salem|Sandwich|Saugus|Scituate|Seekonk|Sharon|Shrewsbury|Somerset|Somerville|South Egremont|South Hadley|South Yarmouth|Southborough|Southbridge|Spencer|Springfield|Stockbridge|Stoneham|Stoughton|Sudbury|Swampscott|Swansea|Taunton|Tewksbury|Turners Falls|Tyngsborough|Uxbridge|Vineyard Haven|Wakefield|Walpole|Waltham|Wareham|Warren|Watertown|Wayland|Webster|Wellesley|West Springfield|West Upton|Westborough|Westfield|Westford|Weston|Westport|Westwood|Weymouth|Whitman|Wilbraham|Wilmington|Winchester|Winthrop|Woburn|Worcester|Wrentham|Yarmouth");
		}
		if (state.contains("Michigan") || state.contains("MI")) {
			city = Util.match(addSec,
					"Ada|Adrian|Algonac|Allen Park|Allendale|Alma|Alpena|Alpine|Ann Arbor|Antwerp|Attica|Auburn Hills|Bangor|Battle Creek|Bay City|Bedford|Beecher|Benton Harbor|Benton|Berkley|Berrien Springs|Beverly Hills|Big Rapids|Birmingham|Blackman|Bloomfield Hills|Bloomfield Township|Bloomfield|Brandon|Bridgeport|Brighton|Brimley|Brownstown|Buena Vista|Burton|Byron|Cadillac|Cannon|Canton|Cascade|Charlevoix|Chesterfield|Clawson|Clinton|Coldwater|Commerce|Comstock Park|Comstock|Cutlerville|Davison|De Witt|Dearborn Heights|Dearborn|Delhi|Delta|Detroit|East Grand Rapids|East Lansing|East Tawas|Eastpointe|Eckerman|Ecorse|Emmett|Escanaba|Farmington Hills|Farmington|Fenton|Ferndale|Flint|Flushing|Forest Hills|Fort Gratiot|Frankenmuth|Fraser|Frenchtown|Fruitport|Gaines|Garden City|Garfield|Genesee|Genoa|Georgetown|Grand Blanc|Grand Haven|Grand Rapids|Grandville|Grayling|Green Oak|Grosse Ile|Grosse Pointe Park|Grosse Pointe Woods|Grosse Pointe|Hamburg|Hamtramck|Harbor Springs|Harper Woods|Harrison|Hartland|Haslett|Hazel Park|Highland Park|Highland|Holland|Holly|Holt|Houghton|Howell|Huron|Independence|Inkster|Ionia|Jackson|Jenison|Kalamazoo|Kentwood|Kincheloe|Lansing|Leoni|Lincoln Park|Lincoln|Livonia|Lyon|Macomb|Madison Heights|Marquette|Marshall|Melvindale|Meridian|Midland|Milford|Minden City|Monitor|Monroe|Mount Clemens|Mount Morris|Mount Pleasant|Mundy|Muskegon Heights|Muskegon|Niles|Northview|Northville|Norton Shores|Novi|Oak Park|Oakland|Okemos|Orion|Oscoda|Oshtemo|Owosso|Oxford|Park|Pittsfield|Plainfield|Plymouth Township|Plymouth|Pontiac|Port Huron|Portage|Redford|Rhodes|Riverview|Rochester Hills|Rochester|Rockford|Romulus|Roseville|Royal Oak|Saginaw Township North|Saginaw Township South|Saginaw|Sault Ste. Marie|Scio|Shelby|South Lyon|Southfield|Southgate|Spring Lake|Springfield|Sterling Heights|Sturgis|St. Clair Shores|St. Joseph|Summit|Sumpter|Superior|Taylor|Texas|Thomas|Traverse City|Trenton|Troy|Utica|Van Buren|Vassar|Vienna|Walker|Warren|Washington|Waterford|Waverly|Wayne|West Bloomfield Township|West Bloomfield|Westland|White Lake|Whitehall|Wixom|Woodhaven|Wyandotte|Wyoming|Ypsilanti");
		}
		if (state.contains("Minnesota") || state.contains("MN")) {
			city = Util.match(addSec,
					"Dundas|Albert Lea|Andover|Anoka|Apple Valley|Austin|Bemidji|Blaine|Bloomington|Brainerd|Brook Park|Brooklyn Center|Brooklyn Park|Buffalo|Burnsville|Center City|Champlin|Chanhassen|Chaska|Cloquet|Columbia Heights|Cook|Coon Rapids|Cottage Grove|Cotton|Crystal|Deer River|Detroit Lakes|Duluth|Eagan|East Bethel|Eden Prairie|Edina|Elk River|Fairmont|Faribault|Farmington|Fergus Falls|Fridley|Golden Valley|Graceville|Grand Rapids|Grasston|Ham Lake|Hastings|Hibbing|Hill City|Hopkins|Howard Lake|Hugo|Hutchinson|Inver Grove Heights|Kerrick|Lakeville|Lino Lakes|Long Prairie|Mankato|Maple Grove|Maple Plain|Maplewood|Marshall|Meadowlands|Mendota Heights|Minneapolis|Minnetonka|Monticello|Moorhead|Motley|Mounds View|New Brighton|New Hope|New Ulm|North Mankato|North St. Paul|Northfield|Norwood|Oakdale|Orr|Owatonna|Plymouth|Prior Lake|Ramsey|Red Wing|Richfield|Robbinsdale|Rochester|Roosevelt|Rosemount|Roseville|Rush City|Sauk Centre|Sauk Rapids|Savage|Sebeka|Shakopee|Shevlin|Shoreview|Silver Bay|Solway|South St. Paul|Stillwater|St. Cloud|St. Louis Park|St. Paul|Vadnais Heights|Virginia|West St. Paul|White Bear Lake|White Bear|Willmar|Winona|Woodbury|Worthington|Wrenshall|Young America|Otsego|St. Michael|Minnetrista");
		}
		if (state.contains("Mississippi") || state.contains("MS")) {
			city = Util.match(addSec,
					"Bay Saint Louis|Biloxi|Brandon|Canton|Clarksdale|Cleveland|Clinton|Columbus|Corinth|Drew|Flora|Gautier|Greenville|Greenwood|Grenada|Gulfport|Hattiesburg|Hermanville|Horn Lake|Indianola|Jackson|Laurel|Long Beach|Madison|Magee|McComb|Meridian|Moss Point|Natchez|Ocean Springs|Olive Branch|Oxford|Pascagoula|Pearl|Picayune|Ridgeland|Rolling Fork|Southaven|Starkville|Tupelo|Vicksburg|Walls|West Point|Wiggins|Woodville|Yazoo City|D'iberville|Pass Christian");
		}
		if (state.contains("Missouri") || state.contains("MO")) {
			city = Util.match(addSec,
					"Moberly|Affton|Arnold|Ballwin|Bellefontaine Neighbors|Belton|Berkeley|Blue Springs|Bridgeton|Butler|Cape Girardeau|Carthage|Chesterfield|Chillicothe|Clayton|Columbia|Concord|Craig|Crestwood|Creve Coeur|Excelsior Springs|Farmington|Fenton|Ferguson|Florissant|Forest City|Fort Leonard Wood|Fulton|Gladstone|Grandview|Grant City|Hannibal|Hazelwood|Independence|Jackson|Jefferson City|Jennings|Joplin|Kansas City|Kennett|Kirksville|Kirkwood|Lake St. Louis|Lamar|Lebanon|Lee's Summit|Lemay|Liberty|Manchester|Marshall|Maryland Heights|Maryville|Maysville|Mehlville|Mexico|Neosho|Newtown|Nixa|Norborne|O' Fallon|Oakville|Osceola|Overland|Poplar Bluff|Raymore|Raytown|Rock Port|Rolla|Sedalia|Sikeston|Spanish Lake|Springfield|St. Ann|St. Charles|St. Joseph|St. Louis|St. Peters|Town and Country|Trenton|University City|Warrensburg|Washington|Webster Groves|Wentzville|West Plains|Wildwood");
		}
		if (state.contains("Montana") || state.contains("MT")) {
			city = Util.match(addSec,
					"Absarokee|Arlee|Ashland|Billings|Bonner|Bozeman|Broadus|Butte|Columbia Falls|Cooke City|Cut Bank|Deer Lodge|Dillon|East Helena|Fallon|Frenchtown|Glasgow|Great Falls|Hamilton|Hardin|Helena|Kalispell|Laurel|Lewistown|Miles City|Missoula|Red Lodge|Roberts|Roundup|Scobey|Toston|West Glacier|Winnett");
		}
		if (state.contains("Nebraska") || state.contains("NE")) {
			city = Util.match(addSec,
					"Bassett|Beatrice|Bellevue|Blair|Bruno|Chalco|Columbus|Fremont|Grand Island|Hastings|Holdrege|Kearney|La Vista|Lexington|Lincoln|Lyman|Norfolk|North Bend|North Platte|Omaha|Papillion|Scottsbluff|Sidney|South Sioux City|Valentine|Valley");
		}
		if (state.contains("Nevada") || state.contains("NV")) {
			city = Util.match(addSec,
					"Crystal Bay|Carson City|Henderson|Indian Springs|Jean|Pahrump|Boulder City|Elko|Enterprise|Gardnerville Ranchos|Hawthorne|Las Vegas|North Las Vegas|Reno|Searchlight|Sparks|Spring Creek|Spring Valley|Sun Valley|Sunrise Manor|Whitney|Winchester|Zephyr Cove|Alamo|Amargosa Valley|Ash Springs|Austin|Baker|Battle Mountain|Beatty|Beowawe|Blue Diamond|Boulder City|Bunkerville|Cal-Nev-Ari|Caliente|Carlin|Carson City|Cold Springs|Crystal|Crystal Bay|Dayton|Denio|Duckwater|Dyer|East|Ely|Elko|Empire|Enterprise|Eureka|Fallon|Fernley|Gabbs|Gardnervillle|Gerlach|Golden Valley|Goldfield|Goodsprings|Hawthorne|Henderson|Imlay|Incline Village|Indian Hills|Indian Springs|Jackpot|Jarbidge|Jean|Jiggs|Johnson Lane|Kingsbury|Las Vegas|Laughlin|Lemmon Valley|Logandale|Lovelock|Lund|McDermitt|McGill|Mesquite|Minden|Moapa Town|Moapa Valley|Montello|Mount Charleston|Nixon|North Las Vegas|Orovada|Overton|Owyhee|Pahrump|Panaca|Paradise|Pioche|Rachel|Reno|Round Hill Village|Round Mountain|Sandy Valley|Schurz|Searchlight|Silver Park|Silver Springs|Sloan|Smith|Spanish Springs|Sparks|Spring Creek|Spring Valley|Stateline|Summerlin South|Sun Valley|Sunrise Manor|Sutcliffe|Tonopah|Tuscarora|Verdi|Virginia City|Wadsworth|Wells|West Wendover|Winnemucca|Whitney|Winchester|Yerington|Zephyr Cove");
		}
		if (state.contains("New Hampshire") || state.contains("NH")) {
			city = Util.match(addSec,
					"Amherst|Bedford|Berlin|Claremont|Concord|Conway|Cornish Flat|Derry|Dover|Durham|Exeter|Goffstown|Hampton|Hanover|Hooksett|Hudson|Keene|Laconia|Lebanon|Londonderry|Madison|Manchester|Merrimack|Milford|Nashua|Pelham|Peterborough|Portsmouth|Rochester|Salem|Somersworth|Twin Mountain|Walpole|Windham|Wolfeboro");
		}
		if (state.contains("New Jersey") || state.contains("NJ")) {
			city = Util.match(addSec,
					"Manahawkin|Monroe Township|Evesham Township|Mount Olive|Aberdeen|Allenhurst|Asbury Park|Atlantic City|Avenel|Barclay-Kingston|Barnegat|Basking Ridge|Bayonne|Beachwood|Belleville|Bellmawr|Belmar|Bergenfield|Berkeley Heights|Berkeley|Bernards Township|Bloomfield|Bound Brook|Branchburg|Branchville|Brick|Bridgeton|Bridgewater|Brigantine|Browns Mills|Burlington|Caldwell|Camden|Carteret|Cedar Grove|Chatham|Cherry Hill Mall|Cherry Hill|Cinnaminson|City of Orange|Clark|Cliffside Park|Clifton|Clinton|Collingswood|Colonia|Colts Neck|Cranbury|Cranford|Delran|Denville|Deptford|Dover|Dumont|East Brunswick|East Hanover|East Orange|East Windsor|Eatontown|Echelon|Edison|Egg Harbor Township|Egg Harbor|Elizabeth|Elmwood Park|Englewood|Evesham|Ewing|Fair Lawn|Fairview|Florence|Fords|Fort Lee|Franklin Lakes|Franklin|Freehold|Galloway|Garfield|Glassboro|Glen Rock|Gloucester City|Gloucester|Greentree|Guttenberg|Hackensack|Hackettstown|Haddonfield|Haddon|Haledon|Hamilton|Hammonton|Hanover|Harrison|Hasbrouck Heights|Hawthorne|Hazlet|Highland Park|Hillsborough|Hillsdale|Hillside|Hoboken|Holiday City-Berkeley|Holmdel|Hopatcong|Hopewell|Howell|Irvington|Iselin|Jackson|Jefferson|Jersey City|Keansburg|Kearny|Kenilworth|Kirkwood Voorhees|Lacey|Lakehurst|Lakewood|Lawrence|Leisure Village West-Pine Lake Park|Lincoln Park|Lindenwold|Linden|Linwood|Little Egg Harbor|Little Falls|Little Ferry|Livingston|Lodi|Long Branch|Lower|Lumberton|Lyndhurst|Madison|Mahwah|Manalapan|Manchester|Mantua|Manville|Maple Shade|Maplewood|Marlboro|Marlton|Medford|Mercerville-Hamilton Square|Metuchen|Middlesex|Middletown|Middle|Millburn|Millville|Monroe|Montclair|Montgomery|Montville|Moorestown-Lenola|Moorestown|Morganville|Morristown|Morris|Mount Holly|Mount Laurel|Neptune|New Brunswick|New Milford|New Providence|Newark|North Arlington|North Bergen|North Brunswick Township|North Brunswick|North Plainfield|Nutley|Oakland|Ocean Acres|Ocean City|Ocean|Old Bridge|Orange|Palisades Park|Paramus|Parsippany-Troy Hills|Passaic|Paterson|Pemberton|Pennsauken|Pennsville|Pequannock|Perth Amboy|Phillipsburg|Pine Hill|Piscataway|Plainfield|Plainsboro|Pleasantville|Point Pleasant|Pompton Lakes|Princeton Meadows|Princeton|Rahway|Ramsey|Randolph|Raritan|Readington|Red Bank|Ridgefield Park|Ridgefield|Ridgewood|Ringwood|River Edge|Riverside|Rockaway|Roselle Park|Roselle|Roxbury|Rutherford|Saddle Brook|Sayreville|Scotch Plains|Secaucus|Somers Point|Somerset|Somerville|South Amboy|South Brunswick|South Orange Village|South Orange|South Plainfield|South River|Southampton|Sparta|Springdale|Springfield|Stafford|Succasunna-Kenvil|Summit|Teaneck|Tenafly|Tinton Falls|Toms River|Totowa|Trenton|Union City|Union|Upper|Ventnor City|Vernon|Verona|Vineland|Voorhees|Wallington|Wall|Wanaque|Wantage|Warren|Washington|Waterford|Wayne|Weehawken|West Caldwell|West Deptford|West Freehold|West Milford|West New York|West Orange|West Paterson|West Windsor|Westfield|Westwood|Williamstown|Willingboro|Winslow|Woodbridge|Woodbury|Wyckoff|Egg Harbor Township|Manchester Township");
		}
		if (state.contains("New Mexico") || state.contains("NM")) {
			city = Util.match(addSec,
					"Deming|Alamogordo|Albuquerque|Anthony|Artesia|Bernalillo|Carlsbad|Cloudcroft|Clovis|Cuba|Eagle Nest|Espanola|Estancia|Farmington|Gallup|Grants|Hobbs|La Mesa|Las Cruces|Las Vegas|Lordsburg|Los Alamos|Los Lunas|Moriarty|North Valley|Penasco|Peralta|Portales|Rio Rancho|Roswell|Santa Fe|Santa Rosa|Silver City|South Valley|Springer|Sunland Park|Tucumcari|Wagon Mound|Watrous");
		}
		if (state.contains("New York") || state.contains("NY")) {
			city = Util.match(addSec,
					"Medford|Clay|Albany|Alden|Amherst|Amsterdam|Antwerp|Arcadia|Arlington|Auburn|Aurora|Babylon|Baldwin|Batavia|Bath|Bay Shore|Beacon|Bedford|Beekman|Bellmore|Bethlehem|Bethpage|Binghamton|Blooming Grove|Brentwood|Brighton|Bronx|Brookhaven|Brooklyn|Brunswick|Buffalo|Camillus|Canandaigua|Canton|Carmel|Carthage|Catskill|Centereach|Central Islip|Cheektowaga|Chenango|Chester|Chili|Cicero|Clarence|Clarkstown|Clifton Park|Cohoes|Colonie|Commack|Copiague|Coram|Corning|Cornwall|Cortlandt|Cortland|Coxsackie|Croton-On-Hudson|De Witt|Deer Park|Depew|Dix Hills|Dobbs Ferry|Dryden|Dunkirk|East Fishkill|East Greenbush|East Hampton|East Islip|East Massapequa|East Meadow|East Northport|East Patchogue|East Rockaway|Eastchester|Elma|Elmira|Elmont|Elwood|Endicott|Endwell|Evans|Fairmount|Fallsburg|Far Rockaway|Farmingdale|Farmington|Farmingville|Fishkill|Floral Park|Flushing|Fort Drum|Franklin Square|Fredonia|Freeport|Fultonville|Fulton|Garden City|Gates-North Gates|Gates|Geddes|Geneva|Georgetown|German Flatts|Glen Cove|Glens Falls|Glenville|Gloversville|Goshen|Grand Island|Great Neck|Greece|Greenburgh|Greenlawn|Groveland|Guilderland|Halfmoon|Hamburg|Hampton Bays|Harrison|Hauppauge|Haverstraw|Hempstead|Henrietta|Hicksville|Highlands|Holbrook|Holtsville|Horseheads|Huntington Station|Huntington|Hyde Park|Irondequoit|Islip|Ithaca|Jamaica|Jamestown|Jefferson Valley-Yorktown|Jericho|Johnson City|Keeseville|Kenmore|Kent|Kings Park|Kingsbury|Kingston|Kirkland|Kiryas Joel|La Grange|Lackawanna|Lake Grove|Lake Ronkonkoma|Lancaster|Lansing|Latham|Le Ray|Levittown|Lewisboro|Lewiston|Lindenhurst|Liverpool|Lockport|Long Beach|Long Island City|Lynbrook|Lyon Mountain|Lysander|Malone|Malta|Mamakating|Mamaroneck|Manhattan|Manlius|Manorville|Massapequa Park|Massapequa|Massena|Mastic Beach|Mastic|Melville|Merrick|Middletown|Miller Place|Milton|Mineola|Monroe|Monsey|Montgomery|Moreau|Mount Pleasant|Mount Vernon|Nanuet|Nesconset|New Cassel|New Castle|New City|New Hartford|New Hyde Park|New Paltz|New Rochelle|New Windsor|New York|Newburgh|Newcomb|Niagara Falls|Niskayuna|North Amityville|North Babylon|North Bangor|North Bay Shore|North Bellmore|North Castle|North Greenbush|North Hempstead|North Lawrence|North Lindenhurst|North Massapequa|North Merrick|North New Hyde Park|North Tonawanda|North Valley Stream|North Wantagh|Oceanside|Ogdensburg|Ogden|Olean|Oneida|Oneonta|Onondaga|Orangetown|Orchard Park|Ossining|Oswego|Owego|Oyster Bay|Parma|Patchogue|Patterson|Pearl River|Peekskill|Pelham|Penfield|Perinton|Pittsford|Plainview|Plattsburgh|Pleasantville|Pomfret|Port Chester|Port Washington|Potsdam|Poughkeepsie|Putnam Valley|Queensbury|Queens|Ramapo|Red Hook|Rexford|Ridge|Riverhead|Rochester|Rockville Centre|Rocky Point|Rodman|Rome|Ronkonkoma|Roosevelt|Rotterdam|Rye|Salina|Salisbury|Saranac Lake|Saratoga Springs|Saugerties|Sayville|Scarsdale|Schenectady|Schodack|Seaford|Selden|Setauket-East Setauket|Shawangunk|Shirley|Smithtown|Somers|South Farmingdale|Southampton|Southeast|Southold|Southport|Spring Valley|Staten Island|Stephentown|Stony Brook|Stony Point|St. James|Suffern|Sullivan|Sweden|Syosset|Syracuse|Tarrytown|Terryville|Thompson|Ticonderoga|Tonawanda|Troy|Ulster|Uniondale|Union|Utica|Valley Stream|Van Buren|Vestal|Wallkill|Wantagh|Wappinger|Warwick|Watertown|Watervliet|Wawarsing|Webster|West Babylon|West Haverstraw|West Hempstead|West Islip|West Nyack|West Point|West Seneca|Westbury|Westerlo|Wheatfield|White Plains|Whitestown|Wilton|Woodmere|Wyandanch|Yonkers|Yorktown");
		}
		if (state.contains("North Carolina") || state.contains("NC")) {
			city = Util.match(addSec,
					"Sunset Beach|Willow Springs|Stallings|Castle Hayne|Knightdale|Supply|Holden Beach|Holly Ridge|Bolivia|Wesley Chapel|Davie|Forsyth|Marvin|Court King|Clayton|Gibsonville|Oak Ridge|Stanly|Landis|Spencer|Summerfield|Winston- Salem|Glen Lane|Elon|Pineville|Morrisville|Troutman|Locust|McLeansville|Waxhaw|Sherrills Ford|Midland|Wando|Wendell|Pfafftown|Whitsett|Albemarle|Apex|Asheboro|Asheville|Banner Elk|Boone|Burlington|Carrboro|Cary|Chapel Hill|Charlotte|Clemmons|Concord|Cornelius|Creedmoor|Durham|Denver|Dallas|Eden|Elizabeth City|Elm City|Fayetteville|Fort Bragg|Garner|Gastonia|Goldsboro|Graham|Greensboro|Greenville|Havelock|Hendersonville|Henderson|Hertford|Hickory|High Point|Haw River|Hope Mills|Huntersville|Indian Trail|Jacksonville|Kannapolis|Kernersville|Kinston|Laurinburg|Lenoir|Lexington|Lincolnton|Lumberton|Mount Holly|Masonboro|Matthews|Mount HollyMint Hill|Monroe|Mooresville|Morganton|Mount Airy|New Bern|Newton|North Wilkesboro|Piney Green|Pittsboro|Raeford|Raleigh|Reidsville|Roanoke Rapids|Rocky Mount|Rural Hall|Salisbury|Sanford|Sealevel|Shallotte|Shelby|Smithfield|Southern Pines|Southport|Statesville|Tarboro|Thomasville|Wake Forest|Wilmington|Wilson|Winston-Salem|Carolina Shores|Davidson|Belmont|Bunnlevel|Spring Lake|Winston Salem|Swansboro|Sneads Ferry|Zebulon|Leland|Hampstead|Mint Hill|Ogden");
		}
		if (state.contains("North Dakota") || state.contains("ND")) {
			city = Util.match(addSec,
					"Bismarck|Dickinson|Fargo|Grand Forks|Gwinner|Jamestown|Mandan|Michigan|Minot|Wahpeton|West Fargo|Williston|Mint Hill");
		}
		if (state.contains("Ohio") || state.contains("OH")) {
			city = Util.match(addSec,
					"Akron|Alliance|Amesville|Amherst|Ashland|Ashtabula|Athens|Aurora|Austintown|Avon Lake|Avon|Barberton|Bay Village|Beachwood|Beavercreek|Bedford Heights|Bedford|Bellefontaine|Berea|Bexley|Blue Ash|Boardman|Bowling Green|Brecksville|Bridgetown North|Broadview Heights|Brook Park|Brooklyn|Brunswick|Bucyrus|Cambridge|Canton|Celina|Centerville|Chillicothe|Cincinnati|Circleville|Clayton|Cleveland Heights|Cleveland|Columbus|Conneaut|Continental|Coshocton|Cuyahoga Falls|Dayton|Defiance|Delaware|Dover|Dublin|East Cleveland|East Liverpool|Eastlake|Elyria|Englewood|Euclid|Fairborn|Fairfield|Fairview Park|Findlay|Finneytown|Forest Park|Forestville|Fostoria|Franklin|Fremont|Gahanna|Galion|Garfield Heights|Germantown|Girard|Greenfield|Greenville|Green|Grove City|Hamilton|Hilliard|Huber Heights|Hudson|Ironton|Kent|Kettering|Lakewood|Lancaster|Landen|Langsville|Laurelville|Lebanon|Leipsic|Lima|Lorain|Loveland|Lucasville|Lyndhurst|Mansfield|Maple Heights|Marietta|Marion|Marysville|Mason|Massillon|Maumee|Mayfield Heights|Medina|Mentor|Miamisburg|Middleburg Heights|Middletown|Montgomery|Mount Vernon|Nelsonville|New Philadelphia|Newark|Niles|North Canton|North College Hill|North Olmsted|North Ridgeville|North Royalton|Northbrook|Northfield|Norton|Norwalk|Norwood|Novelty|Oregon|Oxford|Painesville|Parma Heights|Parma|Pataskala|Perrysburg|Piqua|Portsmouth|Ravenna|Rawson|Reading|Reynoldsburg|Richmond Heights|Riverside|Rocky River|Rossburg|Salem|Sandusky|Seven Hills|Shaker Heights|Sharonville|Shiloh|Sidney|Solon|South Euclid|Springboro|Springdale|Springfield|Steubenville|Stow|Streetsboro|Strongsville|Struthers|Sylvania|Tallmadge|Tiffin|Tipp City|Toledo|Trotwood|Troy|Twinsburg|University Heights|Upper Arlington|Urbana|Van Wert|Vandalia|Vermilion|Wadsworth|Warrensville Heights|Warren|Washington|West Carrollton City|Westerville|Westlake|White Oak|Whitehall|Wickliffe|Willard|Willoughby|Willowick|Wilmington|Wooster|Worthington|Xenia|Youngstown|Zanesville");
		}
		if (state.contains("Oklahoma") || state.contains("OK")) {
			city = Util.match(addSec,
					"Piedmont|Ada|Altus|Ardmore|Atoka|Bartlesville|Beaver|Bethany|Bixby|Broken Arrow|Chickasha|Chouteau|Claremore|Del City|Duncan|Durant|Edmond|El Reno|Elk City|Enid|Guymon|Lawton|McAlester|Miami|Midwest City|Moore|Muskogee|Mustang|Norman|Nowata|Oklahoma City|Okmulgee|Owasso|Pawhuska|Ponca City|Pryor|Sand Springs|Sapulpa|Seminole|Shawnee|Stillwater|Tahlequah|The Village|Tulsa|Wagoner|Woodward|Yukon|Choctaw");
		}
		if (state.contains("Oregon") || state.contains("OR")) {
			city = Util.match(addSec,
					"Clackamas|Albany|Aloha|Altamont|Arlington|Ashland|Baker|Banks|Beaverton|Bend|Burns|Canby|Cannon Beach|Cave Junction|Cedar Mill|Central Point|Chiloquin|Cloverdale|Coos Bay|Corvallis|Cottage Grove|Dallas|Eugene|Forest Grove|Four Corners|Gladstone|Grants Pass|Gresham|Hayesville|Heppner|Hermiston|Hillsboro|Jefferson|Keizer|Klamath Falls|La Grande|Lake Oswego|Lebanon|Lincoln City|Lowell|Lyons|McMinnville|Medford|Mill City|Milwaukie|Newberg|Newport|Oak Grove|Oatfield|Ontario|Oregon City|Pendleton|Portland|Prairie City|Redmond|Roseburg|Salem|Sherwood|Siletz|Silver Lake|Springfield|St. Helens|The Dalles|Tigard|Tillamook|Troutdale|Tualatin|Waldport|West Linn|Wilsonville|Woodburn|Happy Valley|Scappoose|Lancaster|Newtown Square|Bensalem");
		}
		if (state.contains("Pennsylvania") || state.contains("PA")) {
			city = Util.match(addSec,
					"Newtown Square|Abington|Aliquippa|Allentown|Altoona|Antrim|Ardmore|Ashville|Aston|Back Mountain|Baldwin|Beaver Springs|Beech Creek|Bellefonte|Bensalem|Berwick|Bethel Park|Bethlehem|Bloomsburg|Blue Bell|Blue Ridge Summit|Boswell|Boyers|Brentwood|Bristol|Broomall|Buckingham|Butler|Caln|Camp Hill|Carlisle|Carnot-Moon|Center|Chambersburg|Cheltenham|Chester|Chestnuthill|Coal|Coatesville|Collegeville|Colonial Park|Columbia|Concordville|Conshohocken|Coolbaugh|Cornwall|Cranberry|Creekside|Cumru|Dallas|Danville|Darby|Derry|Dover|Downingtown|Doylestown|Drexel Hill|Dry Run|Dunmore|East Goshen|East Hempfield|East Lampeter|East Norriton|East Pennsboro|Easton|Easttown|Edinboro|Elizabethtown|Elizabeth|Emmaus|Ephrata|Erie|Exeter|Exton|Fairview|Falls|Ferguson|Fernway|Fort Washington|Franconia|Franklin Park|Frenchville|Fullerton|Genesee|Gettysburg|Greene|Greensburg|Guilford|Hampden|Hampton Township|Hampton|Hanover|Harborcreek|Harleysville|Harrisburg|Harrison Township|Harrison|Hatfield|Haverford|Hazleton|Hempfield|Hermitage|Hershey|Hilltown|Holmes|Hopewell|Horsham|Hustontown|Hyndman|Indiana|Irvine|Irvona|Jeannette|Jersey Shore|Johnstown|Karthaus|King of Prussia|Kingston|Kittanning|Lake Lynn|Lancaster|Langhorne|Lansdale|Lansdowne|Lebanon|Leesport|Levittown|Limerick|Logan|Lower Allen|Lower Burrell|Lower Gwynedd|Lower Macungie|Lower Makefield|Lower Merion|Lower Moreland|Lower Paxton|Lower Pottsgrove|Lower Providence|Lower Salford|Lower Southampton|Loyalsock|Manchester|Manheim|Manor,|Marple|McCandless Township|McCandless|McKeesport|Meadville|Media|Middle Smithfield|Middletown|Mill Hall|Millcreek|Montgomeryville|Montgomery|Moon|Morrisville|Mount Jewett|Mount Lebanon|Mount Pleasant|Mountain Top|Muhlenberg|Muncy Valley|Munhall|Nanticoke|Needmore|Nether Providence Township|Nether Providence|New Britain|New Castle|New Kensington|New Wilmington|Newberry|Newtown|Norristown|North Bend|North Fayette|North Huntingdon|North Lebanon|North Middleton|North Strabane|North Union|North Versailles|North Wales|North Whitehall|Northampton|Nuangola|Oil City|Olanta|Osceola Mills|Palmer|Patton|Penn Hills|Penn|Peters|Philadelphia|Phoenixville|Pittsburgh|Pittston|Plains|Plumstead|Plum|Plymouth|Pottstown|Pottsville|Punxsutawney|Quarryville|Radnor Township|Radnor|Reading|Richland|Ridley|Robinson Township|Robinson|Ronks|Ross Township|Ross|Rostraver|Salisbury|Sandy|Scott Township|Scott|Scranton|Sewickley|Shaler Township|Shaler|Shamokin|Sharon|Shiloh|Silver Spring|Souderton|South Fayette|South Middleton|South Park Township|South Park|South Union|South Whitehall|Southampton|Spring Garden|Spring Grove|Springettsbury|Springfield|Spring|State College|Stroud|St. Marys|Sunbury|Susquehanna|Swatara|Towamencin|Tredyffrin|Trout Run|Twin Rocks|Uniontown|Unity|Upper Allen|Upper Chichester|Upper Darby|Upper Dublin|Upper Gwynedd|Upper Macungie|Upper Merion|Upper Moreland|Upper Providence Township|Upper Providence|Upper Saucon|Upper Southampton|Upper St. Clair|Uwchlan|Valley Forge|Warminster|Warrendale|Warren|Warrington|Warwick|Washington|Wayne|Weigelstown|West Bradford|West Chester|West Deer|West Goshen|West Hempfield|West Lampeter|West Manchester|West Mifflin|West Milton|West Norriton|West Whiteland|Westfield|Westtown|Whitehall|Whitemarsh|White|Whitpain|Wilkes-Barre|Wilkinsburg|Williamsport|Willistown|Willow Grove|Windsor|Woodlyn|Yeadon|York");
		}
		if (state.contains("Rhode Island") || state.contains("RI")) {
			city = Util.match(addSec,
					"Barrington|Bristol|Burrillville|Central Falls|Coventry|Cranston|Cumberland|East Greenwich|East Providence|Harrisville|Johnston|Lincoln|Middletown|Narragansett|Newport East|Newport|North Kingstown|North Providence|North Smithfield|Pawtucket|Portsmouth|Providence|Scituate|Smithfield|South Kingstown|Tiverton|Valley Falls|Wakefield|Warren|Warwick|West Warwick|Westerly|Woonsocket");
		}
		if (state.contains("South Carolina") || state.contains("SC")) {
			city = Util.match(addSec,
					"Pelzer|Johns Island|Inman|Woodruff|West Ashley|Huger|Simpsonville|Powdersville|Graniteville|Monks Corner|Pineville|Clover|Hopkins|Lyman|Boiling Springs|Wellford|Isle of Palms|Lancaster|Wando|Aiken|Anderson|Beaufort|Berea|Cayce|Charleston|Clemson|Columbia|Conway|Darlington|Dentsville|Easley|Florence|Forest Acres|Gaffney|Gantt|Georgetown|Goose Creek|Greenville|Greenwood|Greer|Hanahan|Hilton Head|Hilton Head Island|Hodges|Honea Path|Irmo|Ladson|Mauldin|Mount Pleasant|Moore|Myrtle Beach|Newberry|North Augusta|North Charleston|North Myrtle Beach|Orangeburg|Parker|Red Hill|Ridgeland|Rock Hill|Seneca|Seven Oaks|Simpsonville|Socastee|Spartanburg|St. Andrews|Summerville|Sumter|Taylors|Wade Hampton|West Columbia|Lake Wylie|Fort Mill|Indian Land|Moncks Corner|Model Homemount Pleasant|James Island|Lexington|Blythewood|Gilbert|Chapin|Elgin|Piedmont|Fountain Inn|Williamston|Duncan|Belton|Ladys Island|Bluffton|Murrells Inlet|Longs|Little River|Surfside Beach|Pawleys Island|Tega Cay|York");
		}
		if (state.contains("South Dakota") || state.contains("SD")) {
			city = Util.match(addSec,
					"Aberdeen|Belle Fourche|Beresford|Box Elder|Brandon|Brookings|Canton|Chamberlain|Dell Rapids|Hot Springs|Huron|Lead|Lennox|Madison|Milbank|Mitchell|North Sioux City|Pierre|Rapid City|Redfield|Sioux Falls|Sisseton|Spearfish|Sturgis|Vermillion|Watertown|Winner|Yankton");
		}
		if (state.contains("Tennessee") || state.contains("TN")) {
			city = Util.match(addSec,
					"Lenoir City|Pleasant View|Mascot|Corryton|Cleveland|Ardmore|Athens|Bartlett|Bloomingdale|Bolivar|Brentwood|Bristol|Brownsville|Chattanooga|Church Hill|Clarksville|Collierville|Columbia|Cookeville|Crossville|Dickson|Dyersburg|East Brainerd|East Ridge|Elgin|Elizabethton|Farragut|Franklin|Gallatin|Germantown|Goodlettsville|Greeneville|Hendersonville|Jackson|Johnson City|Kingsport|Knoxville|La Vergne|Lawrenceburg|Lebanon|Lewisburg|Madison|Martin|Maryville|McMinnville|Memphis|Middle Valley|Millington|Morristown|Mount Juliet|Mountain City|Murfreesboro|Nashville|Oak Ridge|Red Bank|Sevierville|Shelbyville|Smyrna|Sneedville|Soddy-Daisy|Springfield|Tullahoma|Union City|Cane Ridge|Spring Hill|Nolensville");
		}
		if (state.contains("Texas") || state.contains("TX")) {
			city = Util.match(addSec,
					"Pinehurst|Fulshear|Dripping Springs|Bee Cave|Aubrey|Red Oak|New Caney|Argyle|Hackberry|Rosharon|Haslet|Midlothian|Aledo|Lorena|Rosenburg|Spring,|Willis|Lavon|Luling|Ponder|Heath,|Nevada|Anna|Abilene|Addison|Alamo|Aldine|Alice|Allen|Alpine|Alvin|Amarillo|Anderson|Angleton|Arlington|Atascocita|Athens|Austin,|Balch Springs|Bay City|Baytown|Beaumont|Bedford|Beeville|Bellaire|Belton|Benbrook|Big Spring|Boerne|Borger|Brenham|Brownsville|Brownwood|Brushy Creek|Bryan|Burkburnett|Burleson|Canyon Lake|Carrollton|Castroville|Cedar Hill|Cedar Park|Channelview|Channing|Cinco Ranch|Cleburne|Cleveland|Cloverleaf|Clute|College Station|Colleyville|Columbus|Conroe|Converse|Coppell|Copperas Cove|Corinth|Corpus Christi|Corsicana|Cotulla|Cypress|Dallas|Deer Park|Del Rio|Denison|Denton|DeSoto|Dickinson|Donna|Dumas|Duncanville|Eagle Pass|Edinburg|El Campo|El Paso|Euless|Edgecliff Village|Farmers Branch|Flower Mound|Forest Hill|Fort Hood|Fort Worth|Freeport|Friendswood|Frisco|Gainesville|Galena Park|Galveston,|Garland|Gatesville|Georgetown|Gillett|Grand Prairie|Grapevine|Greenville|Groves|Haltom City|Harker Heights|Harlingen|Henderson|Hereford|Hewitt|Hickory Creek|Highland Village|Houston|Humble|Huntsville|Hurst|Irving|Jacinto City|Jacksonville|Jollyville|Katy,|Keller|Kenedy|Kerrville|Kilgore|Killeen|Kingsville|La Homa|La Marque|La Porte|Lake Jackson|Lancaster|Laredo|League City|Leander|Levelland|Lewisville|Lockhart|Longview|Lubbock|Lufkin|Mansfield|Marshall|McAllen|McKinney|Mercedes|Mesquite|Midland|Mineral Wells|Mission Bend|Mission|Missouri City|Mount Pleasant|Nacogdoches|Navasota|Nederland|New Braunfels|New Territory|North Richland Hills|Odessa|Orange|Palestine|Pampa|Paris|Pasadena|Pearland|Pecan Grove|Pflugerville|Pharr|Plainview|Plano|Port Arthur|Port Isabel|Port Lavaca|Port Neches|Portland|Presidio|Raymondville|Richardson|Richmond|Rio Grande City|Roanoke|Robstown|Rockwall|Rosenberg|Round Rock|Round Top|Rowlett|Saginaw|San Angelo|San Antonio|San Benito|San Elizario|San Marcos|Santa Fe|Schertz|Seagoville|Seguin|Sherman|Slaton|Snyder|Socorro|Somerville|South Houston|Southlake|Spring|Stafford|Stamford|Stephenville|Sudan|Sugar Land|Sulphur Springs|Sweetwater|Taylor|Temple|Terrell|Texarkana|Texas City|The Colony|The Woodlands|Three Rivers|Tomball|Tyler|Universal City|University Park|Uvalde|Vernon|Victoria|Vidor|Waco|Watauga|Waxahachie|Weatherford|Wells Branch|Weslaco|West Odessa|West University Place|White Settlement|Wichita Falls|Wilson|Wylie|Elgin|Manor,|Kyle|Briarcreek|Hutto|Bastrop|Sales Office For Info|Spicewood|Oak Point|Princeton|Little Elm|Josephine|Royse City|Van Alstyne|Crossroads|Melissa|Celina|Prosper|Forney|Nevada|Fate|Sachse|Lucas|Horizon City|Jarrell|Azle|Glenn Heights|Northlake|Magnolia|Manvel|Porter|Fresno|Iowa Colony|Montgomery|Cibolo|Troy|Farmersville|Winters");
		}
		if (state.contains("Utah") || state.contains("UT")) {
			city = Util.match(addSec,
					"Syracuse|Mapleton|Saratoga Springs|Altamont|American Fork|Blanding|Bountiful|Brigham City|Canyon Rim|Cedar City|Centerville|Clearfield|Clinton|Cottonwood Heights|Cottonwood West|Draper|East Millcreek|Farmington|Hatch|Holladay|Kamas|Kanab|Kaysville|Kearns|Layton|Lehi|Logan|Magna|Midvale|Millcreek|Monroe|Morgan|Murray|North Ogden|Ogden|Oquirrh|Orem|Park City|Payson|Pleasant Grove|Provo|Richfield|Riverton|Roy|Salt Lake City|Sandy|South Jordan|South Ogden|South Salt Lake|Spanish Fork|Springville|St. George|Taylorsville|Tooele|Vernal|West Jordan|West Valley City|Herriman|Bluffdale|South Weber|Highland|North Salt Lake|Saratoga,");
		}
		if (state.contains("Vermont") || state.contains("VT")) {
			city = Util.match(addSec,
					"Barton|Bennington|Brattleboro|Burlington|Canaan|Chester|Colchester|East Montpelier|Eden|Essex Junction|Essex|Fair Haven|Hartford|Middlebury|Montpelier|Morrisville|Poultney|Readsboro|Rutland|South Burlington|Townshend");
		}
		if (state.contains("Virginia") || state.contains("VA")) {
			city = Util.match(addSec,
					"Aldie|Alexandria|Annandale|Arlington|Bailey's Crossroads|Blacksburg|Bon Air|Bowling Green|Boydton|Bristol|Bull Run|Burke|Carrsville|Cave Spring|Centreville|Chantilly|Charlottesville|Chesapeake|Chester|Chincoteague|Christiansburg|Colonial Heights|Dale City|Danville|Dillwyn|East Highland Park|Fairfax|Falls Church|Farmville|Floyd|Stafford|Ford|Fort Hunt|Franconia|Fredericksburg|Front Royal|Glen Allen|Goochland|Groveton|Grundy|Hampton|Harrisonburg|Herndon|Highland Springs|Hollins|Hopewell|Hybla Valley|Idylwood|Jefferson|Keswick|King George|Lake Ridge|Lakeside|Laurel|Leesburg|Lincolnia|Lorton|Louisa|Lynchburg|Madison Heights|Madison|Manassas Park|Manassas|Martinsville|McLean|Meadowview|Mechanicsville|Merrifield|Middletown|Midlothian|Montclair|Montross|Mount Vernon|Newington|Newport News|Norfolk|Norton|Oak Hall|Oakton|Orange|Petersburg|Poquoson|Portsmouth|Purcellville|Quantico|Radford|Red Ash|Reston|Richmond|Roanoke|Rose Hill|Roseland|Salem|Saluda|Scottsville|Sperryville|Springfield|Staunton|Suffolk|Tazewell|Timberlake|Tuckahoe|Tysons Corner|Unionville|Vienna|Virginia Beach|Waynesboro|West Point|West Springfield|Williamsburg|Winchester|Wolf Trap|Woodbridge|Yorktown|Gainesville|Ashburn|Dumfries|Dulles|Oak Hill|Dunn Loring");
		}
		if (state.contains("Washington") || state.contains("WA")) {
			city = Util.match(addSec,
					"Cle Elum|Granite Falls|Bainbridge Island|Graham|Edgewood|Ridgefield|Battle Ground|Orting|Aberdeen|Alderwood Manor|Anacortes|Arlington|Auburn|Bainbridge Island|Bellevue|Bellingham|Bothell|Bow|Bremerton|Bryn Mawr-Skyway|Burien|Camano|Camas|Cascade-Fairwood|Centralia|Cottage Lake|Covington|Creston|Des Moines|Dishman|East Hill-Meridian|East Renton Highlands|East Wenatchee Bench|Edmonds|Elk Plain|Ellensburg|Enumclaw|Everett|Farmington|Federal Way|Five Corners|Fort Lewis|Hadlock|Harrington|Hunters|Inglewood-Finn Hill|Issaquah|Kelso|Kenmore|Kennewick|Kent|Kettle Falls|Kingsgate|Kirkland|Lacey|Lacrosse|Lake Forest Park|Lakebay|Lakeland North|Lakeland South|Lakewood|Lea Hill|Long Beach|Longview|Lynnwood|Maple Valley|Martha Lake|Marysville|Mercer Island|Mill Creek|Monroe|Moses Lake|Mount Vernon|Mountlake Terrace|Mukilteo|Naches|Nine Mile Falls|North Creek|North Marysville|Oak Harbor|Odessa|Olympia|Opportunity|Orchards|Othello|Paine Field-Lake Stickney|Parkland|Pasco|Picnic Point-North Lynnwood|Port Angeles|Port Orchard|Prairie Ridge|Pullman|Puyallup|Quincy|Redmond|Renton|Richland|Riverton-Boulevard Park|Rosalia|Salmon Creek|Sammamish|SeaTac|Seattle Hill-Silver Firs|Seattle,|Sequim|Shoreline|Silverdale|South Hill|Spanaway|Spokane|Sprague|Sunnyside|Tacoma|Tukwila|Tumwater|Union Hill-Novelty Hill|University Place|Valleyford|Vancouver|Vashon|Walla Walla|Wenatchee|West Lake Stevens|Lake Stevens|West Valley|White Center|Winthrop|Yakima|Woodinville|Bonney Lake|Snohomish");
		}
		if (state.contains("West Virginia") || state.contains("WV")) {
			city = Util.match(addSec,
					"Martinsburg|Berkeley|Aurora|Beckley|Bluefield|Burnsville|Charleston|Clarksburg|Cross Lanes|Fairmont|French Creek|Harpers Ferry|Huntington|Kingwood|Lewisburg|Martinsburg|Morgantown|Parkersburg|Paw Paw|Pineville|Princeton|Rivesville|Rock Cave|Slatyfork|South Charleston|St. Albans|Teays Valley|Vienna|Weirton|Wheeling|White Sulphur Springs");
		}
		if (state.contains("Wisconsin") || state.contains("WI")) {
			city = Util.match(addSec,
					"Allouez|Amery|Appleton|Ashwaubenon|Baraboo|Beaver Dam|Bellevue Town|Bellevue|Beloit|Bonduel|Brookfield|Brown Deer|Brownsville|Caledonia|Camp Douglas|Cedarburg|Chippewa Falls|Columbus|Cudahy|Dallas|De Pere|De Soto|Dodgeville|Eau Claire|Ellsworth|Ferryville|Fitchburg|Fond du Lac|Fort Atkinson|Franklin|Friendship|Germantown|Glendale|Glenwood City|Grafton|Grand Chute|Green Bay|Greendale|Greenfield|Hales Corners|Hartford|Howard|Iola|Janesville|Kaukauna|Kenosha|Krakow|La Crosse|Lena|Little Chute|Madison|Manitowoc|Marinette|Marshfield|Mason|Menasha|Menomonee Falls|Menomonie|Mequon|Merrill|Middleton|Milwaukee|Mondovi|Monroe|Mount Pleasant|Muskego|Neenah|New Berlin|New Holstein|Oak Creek|Oconomowoc|Onalaska|Oshkosh|Pewaukee|Pleasant Prairie|Plover|Port Washington|Racine|Randolph|Richfield|River Falls|Sheboygan|Shorewood|Somerset|South Milwaukee|Stevens Point|Stoughton|Sun Prairie|Superior|Two Rivers|Viola|Watertown|Waukesha|Waupun|Wausau|Wauwatosa|West Allis|West Bend|Weston|Whitefish Bay|Whitewater|Winter|Wisconsin Rapids");
		}
		if (state.contains("Wyoming") || state.contains("WY")) {
			city = Util.match(addSec,
					"Casper|Cheyenne|Cody|Evanston|Gillette|Green River|Laramie|Rock River|Rock Springs|Sheridan|Basin|Buffalo|Douglas|Kemmerer|Lander|Newcastle|Powell|Rawlins|Riverton|Torrington|Worland");
		}
		return city;
	}

	static String commUrl = "";

	public static String getHardcodedAddress(String builderName, String comUrl) throws Exception {
		commUrl = comUrl;
		String newFile = System.getProperty("user.home") + "/Harcoded Builders/" + builderName + ".csv";
		CsvListReader newFileReader = new CsvListReader(new FileReader(newFile), CsvPreference.STANDARD_PREFERENCE);
		List<String> newCsvRow = null;
		int count = 0;
		while ((newCsvRow = newFileReader.read()) != null) {

			if (count > 0) {
				// U.log(newCsvRow.size());
				// builderName=newCsvRow.get(1);

				String aaa = getBuilderObj(newCsvRow);
				if (aaa != null)
					return aaa;
			}
			count++;
		}

		newFileReader.close();
		return null;
	}

	public static String getBuilderObj(List<String> newCsvRow) {
		if (commUrl.contains(newCsvRow.get(1).trim())) {
			return (newCsvRow.get(2) + "," + newCsvRow.get(3) + "," + newCsvRow.get(4) + "," + newCsvRow.get(5) + ","
					+ newCsvRow.get(6) + "," + newCsvRow.get(7) + "," + newCsvRow.get(8) + "," + newCsvRow.get(9) + ","
					+ newCsvRow.get(13)

			);
		}
		return null;

	}

	public static String sendComment(ArrayList<String> newCsvRow) throws InterruptedException {
		StringBuilder sb = new StringBuilder();
		// U.log(newCsvRow.size());
		for (int i = 1; i < newCsvRow.size(); i++) {

			if (newCsvRow.get(i).trim().replace("-", "").length() < 1) {
				// U.log(i+"==="+newCsvRow.get(i).trim());
				// Thread.sleep(4000);
				switch (i) {

				case 1:
					if (sb.length() > 3)
						sb.append(",Builder Name has Blank");
					else
						sb.append("Builder Name has Blank");
					break;

				case 2:
					if (sb.length() > 3)
						sb.append(",Builder URL has Blank");
					else
						sb.append("Builder URL has Blank");
					break;

				case 3:
					if (sb.length() > 3)
						sb.append(",Community Name has Blank");
					else
						sb.append("Community Name has Blank");
					break;

				case 4:
					if (sb.length() > 3)
						sb.append(",Community URL has Blank");
					else
						sb.append("Community URL has Blank");
					break;

				case 5:
					if (sb.length() > 3)
						sb.append(",Community Type has Blank");
					else
						sb.append("Community Type has Blank");
					break;

				case 6:
					if (sb.length() > 3)
						sb.append(",Address has Blank");
					else
						sb.append("address has Blank");
					break;

				case 7:
					if (sb.length() > 3)
						sb.append(",City has Blank");
					else
						sb.append("City has Blank");
					break;

				case 8:
					if (sb.length() > 3)
						sb.append(",State has Blank");
					else
						sb.append("State has Blank");
					break;

				case 9:
					if (sb.length() > 3)
						sb.append(",Zip has Blank");
					else
						sb.append("zip has Blank");
					break;

				case 10:
					if (sb.length() > 3)
						sb.append(",Lattitude and longitude has Blank");
					else
						sb.append("Lattitude and longitude has Blank");
					break;

				case 12:
					if (sb.length() > 3)
						sb.append(",Geo code has Blank");
					else
						sb.append("Geo code has Blank");
					break;

				case 13:
					if (sb.length() > 3)
						sb.append(",Property type has Blank");
					else
						sb.append("Property type has Blank");
					break;

				case 14:
					if (sb.length() > 3)
						sb.append(",Derived property type has Blank");
					else
						sb.append("Derived property type has Blank");
					break;

				case 15:
					if (sb.length() > 3)
						sb.append(",Property Status has Blank");
					else
						sb.append("Property Status has Blank");
					break;

				case 16:
					if (sb.length() > 3)
						sb.append(",Min Price value has Blank");
					else
						sb.append("Min Price value has Blank");
					break;

				case 17:
					if (sb.length() > 3)
						sb.append(",Max Price value has Blank");
					else
						sb.append("Max Price value has Blank");
					break;

				case 18:
					if (sb.length() > 3)
						sb.append(",Min sqft value has Blank");
					else
						sb.append("Min sqft value has Blank");
					break;

				case 19:
					if (sb.length() > 3)
						sb.append(",Max sqft value has Blank");
					else
						sb.append("Max sqft value has Blank");
					break;

				default:
					break;
				}
			}
		}
		return sb.toString();
	}

	public static boolean isvalideLatLng(String add[], String lat, String lng) throws Exception {

		// ChromeDriver driver2;
		if (lat != "" || lat == null) {
			String link = "https://maps.google.com/maps?q=" + lat + "," + lng;
			String htm = U.getHTML(link);
			String gAdd[] = U.getGoogleAddress(htm);
			U.log(gAdd[0] + " " + gAdd[1] + " " + gAdd[2] + " " + gAdd[3]);
			if (gAdd[3].equals(add[3]))
				return true;
			else if (gAdd[1].equalsIgnoreCase(add[1]) && gAdd[2].equalsIgnoreCase(add[2])) {
				return true;
			}

			else {
				// http://maps.yahoo.com/place/?lat=37.71915&lon=-121.845803&q=37.71915%2C-121.845803
				link = "http://maps.yahoo.com/place/?lat=" + lat + "&lon=" + lng + "&q=" + lat + "," + lng;

				ShatamChrome driver1 = ShatamChrome.getInstance();

				driver1.open(link);
				driver1.getOne("#yucs-sprop_button").click();
				htm = driver1.getHtml();

				// driver2 = new ChromeDriver();
				// driver2.
				String url = driver.getUrl();
				U.log(url);

//				URLDecoder decode = new URLDecoder();
				try {
					url = URLDecoder.decode(url, "UTF-8");
					U.log("Address:" + url);
					U.log(add[0] + " " + add[1] + " " + add[2] + " " + add[3]);
					if (url.contains(add[1]))
						return true;
					if (url.contains(add[3]))
						return true;
				} catch (UnsupportedEncodingException ex) {
					ex.printStackTrace();
				}
				// decode.decode(url);
				// U.log(url);
				// driver.close();
			}
			return false;
			// if(gAdd[1])
		}
		return false;
	}

	private static HttpURLConnection getConn1(String urlPath, HashMap<String, String> customHeaders)
			throws IOException {

		URL url = new URL(urlPath);
		HttpURLConnection conn = (HttpURLConnection) url.openConnection();
		conn.setDoOutput(true);
		conn.addRequestProperty("User-Agent",
				"Mozilla/5.0 (Windows NT 6.1; WOW64; rv:10.0.2) Gecko/20100101 Chrome/10.0.2");
		conn.addRequestProperty("Accept", "text/css,application/xhtml+xml,application/xml,*/*;q=0.9");
		conn.addRequestProperty("Accept-Language", "en-us,en;q=0.5");
		conn.addRequestProperty("Cache-Control", "max-age=0");
		conn.addRequestProperty("Connection", "keep-alive");

		if (customHeaders == null || !customHeaders.containsKey("Referer")) {
			conn.addRequestProperty("Referer", "http://" + url.getHost());
		}
		if (customHeaders == null || !customHeaders.containsKey("Host")) {
			conn.addRequestProperty("Host", url.getHost());
		}

		if (customHeaders != null) {
			for (String headerName : customHeaders.keySet()) {
				conn.addRequestProperty(headerName, customHeaders.get(headerName));
			}
		}

		return conn;
	}// getConn

	public static String[] getLatLngBingMap1(String add[]) throws Exception {

		String addr = add[0] + " " + add[1] + " " + add[2] + " " + add[3];
		ShatamChrome driver1 = null;
		String bingLatLng[] = { "", "" };
		driver1 = ShatamChrome.getInstance();
		driver1.open("http://localhost/bing.html");
		driver1.wait("#txtWhere");
		driver1.getOne("#txtWhere").sendKeys(addr);
		driver1.wait("#btn");
		driver1.getOne("#btn").click();
		String binghtml = driver1.getHtml();
		// driver.wait(1000);
		U.log(binghtml);
		String secmap = U.getSectionValue(binghtml, "<div id=\"resultsDiv\"", "</body>");
		bingLatLng[0] = U.getSectionValue(secmap, "<span id=\"span1\">Latitude:", "<br>").trim();
		bingLatLng[1] = U.getSectionValue(secmap, "<br>Longitude:", "</span>");

		U.log("lat:" + bingLatLng[0]);
		U.log("lng:" + bingLatLng[1]);
		return bingLatLng;
	}

	public static String[] getCoordinatesGoogleApi(String add[]) throws IOException {
		String addr = add[0].trim() + "," + add[1].trim() + "," + add[2].trim();
		addr = "https://maps.googleapis.com/maps/api/geocode/json?address="// 1138
																			// Waterlyn
																			// Drive","Clayton","NC
				+ URLEncoder.encode(addr, "UTF-8");
		U.log(addr);
		String html = U.getHTML(addr);
		String locationSec = U.getSectionValue(html, "location\" : {", "}");
		String lat = U.getSectionValue(locationSec, "\"lat\" :", ",");
		String lng = Util.match(locationSec, "lng\" : (\\-\\d+\\.\\d+)", 1);

		locationSec = U.getSectionValue(html, "northeast\" : {", "}");
		String nLat = U.getSectionValue(locationSec, "\"lat\" :", ",");
		String nLng = Util.match(locationSec, "lng\" : (\\-\\d+\\.\\d+)", 1);

		locationSec = U.getSectionValue(html, "southwest\" : {", "}");
		String sLat = U.getSectionValue(locationSec, "\"lat\" :", ",");
		String sLng = Util.match(locationSec, "lng\" : (\\-\\d+\\.\\d+)", 1);

		String latlng[] = { lat, lng, sLat, sLng, nLat, nLng };

		return latlng;
	}

	public static String[] getCoordinatesZip(String zip) throws IOException {
		// addr = add[0].trim() + "," + add[1].trim() + "," + add[2].trim();
		String addr = "https://maps.googleapis.com/maps/api/geocode/json?address="// 1138
																					// Waterlyn
																					// Drive","Clayton","NC
				+ URLEncoder.encode(zip, "UTF-8");
		U.log(addr);
		String html = U.getHTML(addr);
		String locationSec = U.getSectionValue(html, "location\" : {", "}");
		String lat = U.getSectionValue(locationSec, "\"lat\" :", ",");
		String lng = Util.match(locationSec, "lng\" : (\\-\\d+\\.\\d+)", 1);

		locationSec = U.getSectionValue(html, "northeast\" : {", "}");
		String nLat = U.getSectionValue(locationSec, "\"lat\" :", ",");
		String nLng = Util.match(locationSec, "lng\" : (\\-\\d+\\.\\d+)", 1);

		locationSec = U.getSectionValue(html, "southwest\" : {", "}");
		String sLat = U.getSectionValue(locationSec, "\"lat\" :", ",");
		String sLng = Util.match(locationSec, "lng\" : (\\-\\d+\\.\\d+)", 1);

		String latlng[] = { sLat, sLng, nLat, nLng };

		return latlng;
	}

	public static String getHardcodedAddress1(String builderName, String comUrl) throws Exception {
		commUrl = comUrl;
		String newFile = "/home/shatam-17/Harcoded Builders/" + builderName + ".csv";
		CsvListReader newFileReader = new CsvListReader(new FileReader(newFile), CsvPreference.STANDARD_PREFERENCE);
		List<String> newCsvRow = null;
		int count = 0;
		while ((newCsvRow = newFileReader.read()) != null) {

			if (count > 0) {
				// U.log(newCsvRow.size());
				// builderName=newCsvRow.get(1);

				String aaa = getBuilderObj(newCsvRow);
				if (aaa != null)
					return aaa;
			}
			count++;
		}

		newFileReader.close();
		return null;
	}

	public static String getHTMLwithProxy(String path) throws IOException {

		// System.setProperty("http.proxyHost", "104.130.132.119");
		// System.setProperty("http.proxyPort", "3128");
		// System.setProperty("https.protocols", "TLSv1,TLSv1.1,TLSv1.2");
		path = path.replaceAll(" ", "%20");
		// U.log(" .............."+path);
		// Thread.sleep(4000);
		String fileName = getCache(path);

		// U.log("filename:::" + fileName);

		File cacheFile = new File(fileName);
		if (cacheFile.exists())
			return FileUtil.readAllText(fileName);

		URL url = new URL(path);

		String html = null;

		// chk responce code

		// int respCode = CheckUrlForHTML(path);
		// U.log("respCode=" + respCode);

		{

			Proxy proxy = new Proxy(Proxy.Type.HTTP, new InetSocketAddress("47.252.4.64", 8888));
			final URLConnection urlConnection = url.openConnection(proxy);

			// Mimic browser
			try {
				urlConnection.addRequestProperty("User-Agent",
						"Mozilla/5.0 (Windows NT 6.1; WOW64; rv:10.0.2) Gecko/20100101 Firefox/10.0.2");
				urlConnection.addRequestProperty("Accept", "text/css,*/*;q=0.1");
				urlConnection.addRequestProperty("Accept-Language", "en-us,en;q=0.5");
				urlConnection.addRequestProperty("Cache-Control", "max-age=0");
				urlConnection.addRequestProperty("Connection", "keep-alive");
				// U.log("getlink");
				final InputStream inputStream = urlConnection.getInputStream();

				html = IOUtils.toString(inputStream);
				// final String html = toString(inputStream);
				inputStream.close();

				if (!cacheFile.exists())
					FileUtil.writeAllText(fileName, html);

				return html;
			} catch (Exception e) {
				U.log(e);

			}
			return html;
		}

	}

	public static String sendPostRequestAcceptJson(String requestUrl, String payload) throws IOException {
		String fileName = U.getCache(requestUrl + payload);
		File cacheFile = new File(fileName);
		// log(fileName);
		if (cacheFile.exists())
			return FileUtil.readAllText(fileName);
		StringBuffer jsonString = new StringBuffer();
		try {
			URL url = new URL(requestUrl);
			HttpURLConnection connection = (HttpURLConnection) url.openConnection();
			connection.setDoInput(true);
			connection.setDoOutput(true);
			connection.setRequestMethod("POST");
			connection.setRequestProperty("content-type", "application/json");
			connection.setRequestProperty("referer",
					"https://www.lennar.com/find-a-home?homeCoords=76.96377741792551%2C-48.206937616576994%2C-39.492261192976244%2C-149.105375116577&commCoords=76.96377741792551%2C-48.206937616576994%2C-39.492261192976244%2C-149.105375116577&zoom=3");
			connection.setRequestProperty("origin", "https://www.lennar.com");
//	        connection.setRequestProperty("x-requested-with", "XMLHttpRequest");
//	        connection.setRequestProperty("user-agent", "Mozilla/5.0 (X11; Linux x86_64) AppleWebKit/537.36 (KHTML, like Gecko) Ubuntu Chromium/70.0.3538.77 Chrome/70.0.3538.77 Safari/537.36");
//	        connection.setRequestProperty("cookie", "OT-SessionId=665fec61-b2b7-40fd-88f8-31aa3d514c07; otuvid=A847C352-361E-4A77-BFAA-7EA003EACF6D; notice_preferences=100; notice_behavior=none; _ga=GA1.2.465485978.1562663182; _gid=GA1.2.857089724.1562663182; ak_bmsc=19A565C56B445C5CDEC3FB8164F64981687C36477A1700000859245D58764417~plHir8g753AQ4jnlSBXW7rA28ZxyXq7HPgIJI8QBwx4J+vN9J+CbWshygDarWzDOIhCRc0tWbFqvRAcOHcIPtFR2rHWmobKo+An3LlEE9sPGv3AKJVm75k8/aBvRi3Y8yB4Gh9lfbRmM9qMYfLTwsxhyVz17vM90qmgq1ChYjDIRovuKCYhWmHyDLEHYxd5DPeRrUaAWgEHr4tyhkTkT9rrOBQPLhcHHFjNeIXJfGPnzuUVIN00hsj/atrZtm0GBOC; __gads=ID=c7a0886b4ef2aca9:T=1562663181:S=ALNI_MYU9xFIUi26LDumAw3KFWGyEpFF8Q; OT_dtp_values=covers=2&datetime=2019-07-09 19:00; _csrf=xCJFVNLyun7GmB7I8S9EPSSj; spredCKE=redcount=0; aCKE=4e41c827-cd3e-4917-8f1b-cc5fa7f1f11a; lsCKE=cbref=1&ors=otcom; s_cc=true; s_fid=00AE7231ED57FB36-0EA1676D9565D377; s_dl=1; s_sq=%5B%5BB%5D%5D; s_vi=[CS]v1|2E922EA005031655-60001184E00078BD[CE]; tlrCKE=2019-07-09+09%3a25%3a49Z; s_nr=1562664350445-New; _gat_UA-52354388-1=1; notice_gdpr_prefs=0,1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,21,22,23,24,25,26,27,28,29,30,31,32,33,34,35,36,37,38,39,40,41,42,43,44,45,46,47,48,49,50,51,52,53,54,55,56,57,58,59,60,61,62,63,64,65,66,67,68,69,70,71,72,73,74,75,76,77,78,79,80,81,82,83,84,85,86,87,88,89,90,91,92,93,94,95,96,97,98,99,100:; OT-Session-Update-Date=1562665903; ftc=x=2019-07-09T10%3A51%3A43&c=1&pt1=1&pt2=1&er=0; lvCKE=lvmreg=%2C0&histmreg=57%2C0%7C358%2C0%7C%2C0; bm_sv=503A3652B0E2E4FAB009B06B5CFDEC7C~HdCtMzHuVx9H0mAFZXPyr2ywwoxHzi2bTNE0CdlyXqfdWzuB6JUAC4JHV3kVuKrRIfd4eYvyz2RT/Ckp1lV18FKxCRIFVF68hF6x6ztjjzdawbVB8IBHncGQ8ELCzpQ4nMt/kH0pLsBlgBWlQh7O9MPWznkERaM368kWogh3uww=");
//	        connection.setRequestProperty("Accept", "application/json, text/javascript, */*; q=0.01");
//	        connection.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
			OutputStreamWriter writer = new OutputStreamWriter(connection.getOutputStream(), "UTF-8");
			writer.write(payload);
			writer.close();
			BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream()));
			String line;
			while ((line = br.readLine()) != null) {
				jsonString.append(line);
			}
			br.close();
			connection.disconnect();
		} catch (Exception e) {
			throw new RuntimeException(e.getMessage());
		}
		if (!cacheFile.exists())
			FileUtil.writeAllText(fileName, jsonString.toString());
		return jsonString.toString();
	}

	////////////////////// new Code////////////////////////////////////
	public static String getRedirectedURL(String mainDomain, String url) throws IOException {
		if (!url.contains("http"))
			url = mainDomain + url;
		HttpURLConnection con = (HttpURLConnection) new URL(url).openConnection();
		con.setInstanceFollowRedirects(false);
		con.connect();
		con.getInputStream();

		U.log("response code : " + con.getResponseCode());
		if (con.getResponseCode() == HttpURLConnection.HTTP_MOVED_PERM
				|| con.getResponseCode() == HttpURLConnection.HTTP_MOVED_TEMP) {
			String redirectUrl = con.getHeaderField("Location");
			return getRedirectedURL(mainDomain, redirectUrl);
		}
		return url;
	}

	public static String getGoogleZipFromAddressWithKey(String inputadd[]) throws IOException {

		String st = inputadd[0].trim() + "," + inputadd[1].trim() + "," + inputadd[2].trim();
		String addr = "https://maps.googleapis.com/maps/api/geocode/json?address=" + st
				+ "&key=AIzaSyDhsO7Ska4s4zUW_68R1n8OhRHJuZP5gm8";
		U.log(addr);

		U.log(U.getCache(addr));
		String html = U.getHTML(addr);
		String status = U.getSectionValue(html, "status\" : \"", "\"");

		if (status.trim().equals("OK")) {
			String txt = U.getSectionValue(html, "formatted_address\" : \"", "\"");
			U.log("Address txt Using gmap key :: " + txt);
			if (txt != null)
				txt = txt.trim();
			txt = txt.replace("7970 U.S, Co Rd 11", "7970 US Co Rd 11")
					.replaceAll("The Arden, |TPC Sugarloaf Country Club, ", "").replace("50 Biscayne, 50", "50");
			txt = txt.replaceAll("110 Neuse Harbour Boulevard, ", "");
			txt = txt.replaceAll(
					"Suite CNewark, |Waterview Tower, |Liberty Towers, |THE DYLAN, |Cornerstone, |Roosevelt Towers Apartments, |Zenith, |The George Washington University,|Annapolis Towne Centre, |Waugh Chapel Towne Centre,|Brookstone, |Rockville Town Square Plaza, |University of Baltimore,|The Galleria at White Plains,|Reston Town Center,",
					"");
			String[] add = txt.split(",");
			add[3] = Util.match(add[2], "\\d+");
			add[2] = add[2].replaceAll("\\d+", "").trim();
			return add[3];
		} else {
			return "-";
		}
	}

	public static final String hereApiID = "aGa8KgrWvrUCGqoLCSL9";
	public static final String hereApiCode = "Va551VVoWCaWx7JIMok8eA";

	public static String[] getlatlongHereApi(String add[]) throws IOException {
		String addr = add[0] + "," + add[1] + "," + add[2];
		addr = "https://geocoder.api.here.com/6.2/geocode.json?searchtext=" + URLEncoder.encode(addr, "UTF-8")
				+ "&app_id=" + hereApiID + "&app_code=" + hereApiCode + "&gen=9";
		U.log(addr);
		U.log(U.getCache(addr));
		String html = U.getHTML(addr);
		String sec = U.getSectionValue(html, "\"DisplayPosition\":{", "\"NavigationPosition\":");
		String lat = U.getSectionValue(sec, "\"Latitude\":", ",");
		if (lat != null)
			lat = lat.trim();
		String lng = U.getSectionValue(sec, "\"Longitude\":", "}");
		if (lng != null)
			lng = lng.trim();
		String latlng[] = { lat, lng };
		// U.log(lat);
		return latlng;

	}

	public static String[] getAddressHereApi(String latlng[]) throws Exception {
		String st = latlng[0].trim() + "%2C" + latlng[1].trim()
				+ "%2C100&mode=retrieveAddresses&maxresults=1&gen=9&app_id=" + hereApiID + "&app_code=" + hereApiCode;
		String addr = "https://reverse.geocoder.api.here.com/6.2/reversegeocode.json?prox=" + st;
		U.log(addr);
		U.log(U.getCache(addr));
		String html = U.getPageSource(addr);
		String txt = U.getSectionValue(html, "\"Address\":{\"Label\":\"", "\"");
		U.log("txt:: " + txt);
		if (txt != null)
			txt = txt.trim();
		txt = txt.replaceAll("The Arden, |TPC Sugarloaf Country Club, ", "");
		txt = txt.replaceAll("110 Neuse Harbour Boulevard, ", "");
		txt = txt.replaceAll(
				"Waterview Tower, |Liberty Towers, |THE DYLAN, |Cornerstone, |Roosevelt Towers Apartments, |Zenith, |The George Washington University,|Annapolis Towne Centre, |Waugh Chapel Towne Centre,|Brookstone, |Rockville Town Square Plaza, |University of Baltimore,|The Galleria at White Plains,|Reston Town Center,",
				"");
		String[] add = txt.split(",");
		if (add.length == 5) {
			String newAdd[] = { "", "", "", "" };
			newAdd[0] = add[0] + "," + add[1];
			newAdd[1] = add[2].trim();
			newAdd[3] = Util.match(add[3], "\\d+");
			newAdd[2] = add[3].replaceAll("\\d+", "").trim();
			return newAdd;
		} else {
			add[1] = add[1].trim();
			add[3] = Util.match(add[2], "\\d+");
			add[2] = add[2].replaceAll("\\d+", "").trim();
			return add;
		}
	}

	public static String[] getNewBingLatLong(String[] address) {
		// TODO Auto-generated method stub

		String[] latLong = new String[2];
		String addressLine = address[0] + "," + address[1] + "," + address[2] + "," + address[3];
		if (addressLine == null || addressLine.trim().length() == 0)
			return null;
		addressLine = addressLine.trim().replaceAll("\\+", " ");
		String geocodeRequest = "http://dev.virtualearth.net/REST/v1/Locations/'" + addressLine
				+ "'?o=xml&key=AninuaBy5n6ekoNCLHltcvwcnBGA7llKkNDBNO5XOuHNHJKAyo0BQ8jH_AhhP6Gl";
		// U.log("-----------------addressline-----------"+geocodeRequest);
		try {
			String xml = U.getHTML(geocodeRequest);
			// U.log("--------------------------xml---------------------------------"+xml);
			latLong[0] = U.getSectionValue(xml, "<Latitude>", "</Latitude>");
			latLong[1] = U.getSectionValue(xml, "<Longitude>", "</Longitude>");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return latLong;

	}

	public static String[] getNewBingAddress(String[] latlong) {
		// TODO Auto-generated method stub

		String[] addr = null;
		try {
			String htm = U.getHTML("http://dev.virtualearth.net/REST/v1/Locations/" + latlong[0] + "," + latlong[1]
					+ "?o=json&jsonp=GeocodeCallback&key=AninuaBy5n6ekoNCLHltcvwcnBGA7llKkNDBNO5XOuHNHJKAyo0BQ8jH_AhhP6Gl");
			String[] adds = U.getValues(htm, "formattedAddress\":\"", "\"");
			U.log(htm);
			for (String item : adds) {
				addr = U.getAddress(item);
				if (addr == null || addr[0] == "-")
					continue;
				else {
					U.log("Bing Address =>  Street:" + addr[0] + " City :" + addr[1] + " state :" + addr[2] + " ZIP :"
							+ addr[3]);
					return addr;
				}

			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return addr;
	}

	/**
	 * @author Pallavi Meshram
	 */
	// Format million price
	public static String formatMillionPricesNew(String html) {
//			html="'>From $1.4 m";
		html = html.replaceAll("Million|million", "m");
		Matcher millionPrice = Pattern.compile("\\$\\d\\.(\\d*) m|M", Pattern.CASE_INSENSITIVE).matcher(html);
		String zeroString = "";
		int i;
		while (millionPrice.find()) {
			int count = millionPrice.group(1).length() + 1;
			for (i = 0; i <= 3 - count; i++) {
				zeroString += 0;
			}
			String floorMatch = millionPrice.group().replace(" m| M", zeroString + ",000").replace(".", ","); // $1.3 M
																												// //$1.32
																												// M
																												// //$1.234
																												// M
			html = html.replace(millionPrice.group(), floorMatch);
		} // end millionPrice
//			U.log(html);
		return html;
	}
	
	public static ArrayList<String[]> readCSVFile(String path) throws Exception {
		ArrayList<String[]> ar = new ArrayList<String[]>();
		CsvListReader newFileReader = new CsvListReader(new FileReader(path), CsvPreference.STANDARD_PREFERENCE);
		List<String> listRow = null;

		int count = 0;
		while ((listRow = newFileReader.read()) != null) {
			// if (count > 0)
			{
				// U.log(listRow.get(1));
				String[] aSicData = listRow.toArray(new String[0]);
				ar.add(aSicData);
			}
			count++;

		}
		newFileReader.close();

		// U.log(count);
		return ar;
	}
	//function 
		public static void sleepFive() throws InterruptedException {
			Thread.sleep(1000*60*5);
			
		}
		public static void sleepTen() throws InterruptedException {
			Thread.sleep(1000*60*10);
			
		}
		public static void sleepSix() throws InterruptedException {
			Thread.sleep(1000*60*6);
			
		}
		public static void sleepTwelve() throws InterruptedException {
			Thread.sleep(1000*60*12);
			
		}
}