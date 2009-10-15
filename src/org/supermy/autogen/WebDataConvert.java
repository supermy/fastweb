package org.supermy.autogen;

import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

import javax.persistence.Lob;
import javax.persistence.ManyToOne;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.cfg.annotations.Comment;
import org.hibernate.mapping.Column;
import org.hibernate.mapping.PersistentClass;
import org.hibernate.mapping.Property;
import org.hibernate.validator.CreditCardNumber;
import org.hibernate.validator.Digits;
import org.hibernate.validator.EAN;
import org.hibernate.validator.Email;
import org.hibernate.validator.Future;
import org.hibernate.validator.Length;
import org.hibernate.validator.Max;
import org.hibernate.validator.Min;
import org.hibernate.validator.NotEmpty;
import org.hibernate.validator.NotNull;
import org.hibernate.validator.Past;
import org.hibernate.validator.Pattern;
import org.hibernate.validator.Range;
import org.hibernate.validator.Size;
import org.springframework.security.util.FieldUtils;

public class WebDataConvert {
	private static final Log log = LogFactory.getLog(WebDataConvert.class);
	private static String datePattern = "yyyy-MM-dd";
	private static String uiDatePattern = getDatePattern();

	/**
	 * Generate a random value in a format that makes DbUnit happy.
	 * 
	 * @param column
	 *            the column (i.e. "java.lang.String")
	 * @return a generated string for the particular type
	 */
	public String getTestValueForDbUnit(Column column) {
		StringBuffer result = new StringBuffer();
		String type = column.getValue().getType().getReturnedClass().getName();

		if ("java.lang.Integer".equals(type) || "int".equals(type)) {
			result.append((int) ((Math.random() * Integer.MAX_VALUE)));
		} else if ("java.lang.Float".equals(type) || "float".equals(type)) {
			result.append((float) ((Math.random() * Float.MAX_VALUE)));
		} else if ("java.lang.Long".equals(type) || "long".equals(type)) {
			result.append((long) ((Math.random() * Long.MAX_VALUE)));
		} else if ("java.lang.Double".equals(type) || "double".equals(type)) {
			result.append((double) ((Math.random() * Double.MAX_VALUE)));
		} else if ("java.lang.Short".equals(type) || "short".equals(type)) {
			result.append((short) ((Math.random() * Short.MAX_VALUE)));
		} else if ("java.lang.Byte".equals(type) || "byte".equals(type)) {
			result.append((byte) ((Math.random() * Byte.MAX_VALUE)));
		} else if ("java.lang.Boolean".equals(type) || "boolean".equals(type)) {
			result.append("0");
		} else if ("java.util.Date".equals(type)
				|| "java.sql.Date".equals(type)) {
			result.append(getDate(new Date()));
		} else if ("java.sql.Timestamp".equals(type)) {
			result.append(new Timestamp(new Date().getTime()).toString());
		} else { // default to String for everything else
			String stringWithQuotes = generateStringValue(column);
			result.append(stringWithQuotes.substring(1, stringWithQuotes
					.length() - 1));
		}

		return result.toString();
	}

	/**
	 * Method to generate a random value for use in setting values in a Java
	 * test
	 * 
	 * @param column
	 *            the type of object (i.e. "java.util.Date")
	 * @return The string-ified version of the type
	 */
	public String getValueForJavaTest(Column column) {
		StringBuffer result = new StringBuffer();
		String type = column.getValue().getType().getReturnedClass().getName();

		if ("java.lang.Integer".equals(type)) {
			result.append((int) ((Math.random() * Integer.MAX_VALUE)));
		} else if ("int".equals(type)) {
			result.append("(int) ").append(
					(int) ((Math.random() * Integer.MAX_VALUE)));
		} else if ("java.lang.Float".equals(type)) {
			result.append("new Float(").append(
					(float) ((Math.random() * Float.MAX_VALUE))).append(")");
		} else if ("float".equals(type)) {
			result.append("(float) ").append(
					(float) ((Math.random() * Float.MAX_VALUE)));
		} else if ("java.lang.Long".equals(type)) {
			// not sure why, but Long.MAX_VALUE results in too large a number
			result.append(Math.random() * Integer.MAX_VALUE).append("L");
		} else if ("long".equals(type)) {
			// not sure why, but Long.MAX_VALUE results in too large a number
			result.append((long) ((Math.random() * Integer.MAX_VALUE)));
		} else if ("java.lang.Double".equals(type)) {
			result.append("new Double(").append(
					(Math.random() * Double.MAX_VALUE)).append(")");
		} else if ("double".equals(type)) {
			result.append((Math.random() * Double.MAX_VALUE));
		} else if ("java.lang.Short".equals(type)) {
			result.append("new Short(\"").append(
					(short) ((Math.random() * Short.MAX_VALUE))).append("\")");
		} else if ("short".equals(type)) {
			result.append("(short)").append(
					(short) ((Math.random() * Short.MAX_VALUE)));
		} else if ("java.lang.Byte".equals(type)) {
			result.append("new Byte(\"").append(
					(byte) ((Math.random() * Byte.MAX_VALUE))).append("\")");
		} else if ("byte".equals(type)) {
			result.append("(byte) ").append(
					(byte) ((Math.random() * Byte.MAX_VALUE)));
		} else if ("java.lang.Boolean".equals(type)) {
			result.append("Boolean.FALSE");
		} else if ("boolean".equals(type)) {
			result.append("false");
		} else if ("java.util.Date".equals(type)) {
			result.append("new java.util.Date()");
		} else if ("java.sql.Date".equals(type)) {
			result.append("new java.sql.Date()");
		} else if ("java.sql.Timestamp".equals(type)) {
			result.append("java.sql.Timestamp.valueOf(\"").append(
					new Timestamp(new Date().getTime()).toString()).append(
					"\")");
		} else { // default to String for everything else
			result.append(generateStringValue(column));
		}

		return result.toString();
	}

	/**
	 * Method to generate a random value for use in setting WebTest parameters
	 * 
	 * @param column
	 *            the type of object (i.e. "java.util.Date")
	 * @return The string-ified version of the date
	 */
	public String getValueForWebTest(Column column) {
		String type = column.getValue().getType().getReturnedClass().getName();
		String value = getTestValueForDbUnit(column);
		if (type.equalsIgnoreCase(Date.class.getName())) {
			value = getDate(new Date(), uiDatePattern);
		} else if ("boolean".equals(type) || "java.lang.Boolean".equals(type)) {
			value = "true";
		}

		return value;
	}

	private String generateStringValue(Column column) {
		int maxLen = column.getLength();
		if (maxLen > 5000) {
			log.warn("Column length greater than 5000 characters for '"
					+ column.getName() + "', setting maxlength to 5000.");
			maxLen = 5000;
		}

		StringBuffer result = new StringBuffer("\"");

		for (int i = 0; (i < maxLen); i++) {
			int j = 0;
			if (i % 2 == 0) {
				j = (int) ((Math.random() * 26) + 65);
			} else {
				j = (int) ((Math.random() * 26) + 97);
			}
			result.append(Character.toString((char) j));
		}

		result.append("\"");

		return result.toString();
	}

	public String generateRandomStringValue(Column column) {
		return "\"\" + Math.random()";
	}

	private static String getDate(Date aDate) {
		return getDate(aDate, datePattern);
	}

	private static String getDate(Date aDate, String pattern) {
		SimpleDateFormat df;
		String returnValue = "";

		if (aDate != null) {
			df = new SimpleDateFormat(pattern);
			returnValue = df.format(aDate);
		}

		return returnValue;
	}

	/**
	 * Return default datePattern (MM/dd/yyyy)
	 * 
	 * @return a string representing the date pattern on the UI
	 */
	public static synchronized String getDatePattern() {
		String result;
		try {
			result = ResourceBundle.getBundle("messages", Locale.getDefault())
					.getString("date.format");
		} catch (MissingResourceException mse) {
			result = "MM/dd/yyyy";
		}
		return result;
	}

	/**
	 * Parse a field name and convert it to a titled set of words.
	 * 
	 * @param fieldName
	 *            the pojo.property
	 * @return A string suitable for i18n
	 */
	public String getFieldDescription(String fieldName) {
		StringBuffer buffer = new StringBuffer();
		boolean nextUpper = false;
		for (int i = 0; i < fieldName.length(); i++) {
			char c = fieldName.charAt(i);

			if (i == 0) {
				buffer.append(Character.toUpperCase(c));
				continue;
			}

			if (Character.isUpperCase(c)) {
				buffer.append(' ');
				buffer.append(c);
				continue;
			}

			if (c == '.') {
				buffer.delete(0, buffer.length());
				nextUpper = true;
				continue;
			}

			char x = nextUpper ? Character.toUpperCase(c) : c;
			buffer.append(x);
			nextUpper = false;
		}

		return buffer.toString();
	}

	/**
	 * Get JDBC Type - used by iBATIS in sql-map.ftl
	 * 
	 * @param javaType
	 *            - the Java Class
	 * @return the type to use in a SQL statement
	 */
	public String getJdbcType(String javaType) {
		String jdbcType = "VARCHAR";

		javaType = javaType.toLowerCase();

		if (javaType.indexOf("date") > 0) {
			jdbcType = "TIMESTAMP";
		} else if (javaType.indexOf("timestamp") > 0) {
			jdbcType = "TIMESTAMP";
		} else if ((javaType.indexOf("int") > 0)
				|| (javaType.indexOf("long") > 0)
				|| (javaType.indexOf("short") > 0)) {
			jdbcType = "INTEGER";
		} else if (javaType.indexOf("double") > 0) {
			jdbcType = "DOUBLE";
		} else if (javaType.indexOf("float") > 0) {
			jdbcType = "FLOAT";
		}

		return jdbcType;
	}

	public String required(Property p) {
		String xxx = HValidator2JQValidator(p);
		if (xxx.contains("required"))
			return "<em>*</em>";
		return "<em> </em>";
	}

	/**
	 * Hibernate validate to jquery validate
	 * 
	 * @param p
	 * @return
	 */
	public String HValidator2JQValidator(Property p) {
		String fieldname = p.getName();
		Class<?> classname = p.getPersistentClass().getMappedClass();
		Field field = FieldUtils.getField(classname, fieldname);
		return valid(field);
	}

	/**
	 * 获取注释，用于生成多国语言
	 * 
	 * @param p
	 * @return
	 */
	public String getComment(Property p) {
		String fieldname = p.getName();
		Class<?> classname = p.getPersistentClass().getMappedClass();
		Field field = FieldUtils.getField(classname, fieldname);
		if (field.isAnnotationPresent(Comment.class)) {
			return field.getAnnotation(Comment.class).value();
		}
		return "";// freemarker 不能返回 null 值，否则会抛出异常
	}

	/**
	 * 
	 * @param p
	 * @return
	 */
	public String getCommentDesc(Property p) {
		String fieldname = p.getName();
		Class<?> classname = p.getPersistentClass().getMappedClass();
		Field field = FieldUtils.getField(classname, fieldname);
		if (field.isAnnotationPresent(Comment.class)) {
			return field.getAnnotation(Comment.class).desc();
		}
		return "";// freemarker 不能返回 null 值，否则会抛出异常
	}

	/**
	 * 获取多国语言
	 * 
	 * @param p
	 * @return
	 */
	public String getComment(PersistentClass p) {
		Class<?> classname = p.getMappedClass();
		if (classname.isAnnotationPresent(Comment.class)) {
			return classname.getAnnotation(Comment.class).value();
		}
		return "";// freemarker 不能返回 null 值，否则会抛出异常
	}

	public String getCommentDesc(PersistentClass p) {
		Class<?> classname = p.getMappedClass();
		if (classname.isAnnotationPresent(Comment.class)) {
			return classname.getAnnotation(Comment.class).desc();
		}
		return "";// freemarker 不能返回 null 值，否则会抛出异常
	}

	/**
	 * 类的某个的属性有一个是属于特定类型的
	 * 
	 * @param classname
	 * @param p
	 * @return
	 */
	public boolean fieldHasType(Class<?> classname, String typeName) {
		Field[] declaredFields = classname.getDeclaredFields();
		for (Field field : declaredFields) {
			if (field.getType().getName().equals(typeName)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * @param field
	 * @return
	 */
	public String valid(Field field) {
		StringBuffer valid = new StringBuffer("{debug:false");
		if (field.isAnnotationPresent(CreditCardNumber.class)) {
			valid.append(",creditcard: true");
		}

		// 配合s:select 必须选择验证
		if (field.isAnnotationPresent(ManyToOne.class)) {
			valid.append(",digits:true");
		}

		if (field.isAnnotationPresent(Digits.class)) {// 数字是否符合整数部分和小数部分的精度
			// ,定义列精度和范围
			Digits annotation = field.getAnnotation(Digits.class);
			annotation.fractionalDigits();
			annotation.integerDigits();
			valid.append(",number:true");
		} else {
			if (field.getType().equals(Integer.class)
					|| field.getType().equals(int.class)) {
				valid.append(",digits:true");
			}

			if (field.getType().equals(Number.class)
					|| field.getType().equals(Float.class)
					|| field.getType().equals(Double.class)
					|| field.getType().equals(BigDecimal.class)
					|| field.getType().equals(float.class)
					|| field.getType().equals(double.class)) {
				valid.append(",number:true");
			}
		}

		if (field.isAnnotationPresent(Email.class)
				|| field.getName().toLowerCase().endsWith("email")) {
			if (field.getType().equals(java.lang.String.class))
				valid.append(",email:true");
		}
		if (field.getName().toLowerCase().endsWith("url")
				|| field.getName().toLowerCase().contains("http")) {
			if (field.getType().equals(java.lang.String.class))
				valid.append(",url:true");
		}
		if (field.isAnnotationPresent(EAN.class)) {// 條形碼
		}
		if (field.isAnnotationPresent(Future.class)) {// 日期是否在未來
			valid.append(",date: true");
		}
		if (field.isAnnotationPresent(Past.class)) {// 日期是否在過去
			valid.append(",date:true");
		}

		if (field.getType().equals(java.util.Date.class)) {// 日期
			valid.append(",date:true");
		}

		if (field.isAnnotationPresent(Length.class)) {
			Length annotation = field.getAnnotation(Length.class);
			int maxlength = 250;
			if (annotation.max() == 2147483647) {
				if (field.isAnnotationPresent(javax.persistence.Column.class)) {
					javax.persistence.Column col = field
							.getAnnotation(javax.persistence.Column.class);
					if (col.length() != 255) {
						maxlength = col.length();
					}
					;
				}
			} else
				maxlength = annotation.max();

			valid.append(",maxlength:").append(maxlength);
			valid.append(",minlength:").append(annotation.min());
		}
		if (field.isAnnotationPresent(Max.class)) {
			Max annotation = field.getAnnotation(Max.class);
			valid.append(",max:").append(annotation.value());
		}
		if (field.isAnnotationPresent(Min.class)) {
			Min annotation = field.getAnnotation(Min.class);
			valid.append(",min:").append(annotation.value());
		}
		if (field.isAnnotationPresent(NotEmpty.class)
				|| field.isAnnotationPresent(NotNull.class)) {
			valid.append(",required: true");
		}
		if (field.isAnnotationPresent(Pattern.class)) {
			Pattern annotation = field.getAnnotation(Pattern.class);
			annotation.regex();
			annotation.flags();
		}
		if (field.isAnnotationPresent(Range.class)) {
			Range annotation = field.getAnnotation(Range.class);
			valid.append(",range:[").append(annotation.min()).append(",")
					.append(annotation.max()).append("]");
		}
		if (field.isAnnotationPresent(Size.class)) {// property (数组, 集合, map)
		}

		if (field.getType().equals(String.class)
				&& !field.isAnnotationPresent(Lob.class)) {
			if (valid.indexOf("maxlength:") < 0) {
				int maxlength = 250;
				if (field.isAnnotationPresent(javax.persistence.Column.class)) {
					javax.persistence.Column col = field
							.getAnnotation(javax.persistence.Column.class);
					if (col.length() != 255) {
						maxlength = col.length();
					}
					;
				}
				valid.append(",maxlength:").append(maxlength);
			}
		}

		valid.append("}");
		return valid.toString();
	}

}