package com.qihang.winter.winterdao.spring.map;

import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Locale;
import java.util.Map;

/**
 * 提供默认小写作为Key的Map
 * 
 * @author Zerrion
 * @date 2013-9-27
 * @version 1.0
 */
@SuppressWarnings("rawtypes")
public class WinterDaoLinkedMap extends LinkedHashMap<String, Object> {

	private static final long serialVersionUID = 1L;

	private final Locale locale;

	public WinterDaoLinkedMap() {
		this(((Locale) (null)));
	}

	public WinterDaoLinkedMap(int initialCapacity) {
		this(initialCapacity, null);
	}

	public WinterDaoLinkedMap(int initialCapacity, Locale locale) {
		super(initialCapacity);
		this.locale = locale == null ? Locale.getDefault() : locale;
	}

	public WinterDaoLinkedMap(Locale locale) {
		this.locale = locale == null ? Locale.getDefault() : locale;
	}

	public void clear() {
		super.clear();
	}

	public boolean containsKey(Object key) {
		return (key instanceof String)
				&& super.containsKey(convertKey((String) key));
	}

	protected String convertKey(String key) {
		return key.toLowerCase(locale);
	}

	public Object get(Object key) {
		if (key instanceof String)
			return super.get(convertKey((String) key));
		else
			return null;
	}

	public Object put(String key, Object value) {
		return super.put(convertKey(key), value);
	}

	public void putAll(Map map) {
		if (map.isEmpty())
			return;
		Map.Entry entry;
		for (Iterator iterator = map.entrySet().iterator(); iterator.hasNext(); put(
				convertKey((String) entry.getKey()), entry.getValue()))
			entry = (Map.Entry) iterator.next();

	}

	public Object remove(Object key) {
		if (key instanceof String)
			return super.remove(convertKey((String) key));
		else
			return null;
	}

}
