package Utils;

import java.util.UUID;

import com.amazonaws.services.dynamodbv2.document.Item;

public class Utils {

	public static void extratcValueIfNotNull(String value, Item item, String attribute) {
		if (value != null) {
			item.withString(attribute, value);
		}
	}
	
	public String getHash(String text) {
		if (text == null) {
			return UUID.randomUUID().toString();
		}
		return UUID.nameUUIDFromBytes(text.getBytes()).toString();
	}
	
}
