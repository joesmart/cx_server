package com.server.cx.util;

import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.io.InputStream;
import java.lang.reflect.Method;
import java.sql.Blob;

import org.dom4j.Element;

//bean --> xml
public class XMLReflector {
	@SuppressWarnings("rawtypes")
  private Class sourceClass;
	private BeanInfo beanInfo;
	private String name;
	private String result = "";

	public void setHeader(String head) {
		result = head;
	}

	@SuppressWarnings("rawtypes")
  public XMLReflector(Class sourceClass, String name) throws Exception {
		this.sourceClass = sourceClass;
		this.name = name;
		beanInfo = Introspector.getBeanInfo(sourceClass);
		int len = beanInfo.getPropertyDescriptors().length;
		System.out.println(len);
	}

	public String convertToXml(Object o, Element cxinfos) throws Exception {
		StringBuffer returnValue = new StringBuffer(this.result);
		Element cxinfo = null;
		if (o.getClass().isAssignableFrom(sourceClass)) {
			PropertyDescriptor[] pd = beanInfo.getPropertyDescriptors();
			if (pd.length > 0) {
				if (!name.equals("")) {
					// returnValue.append("<" + name + ">");
					cxinfo = cxinfos.addElement(name);
				}
				for (int i = 0; i < pd.length; i++) {
					returnValue.append(getProp(o, pd[i], cxinfo));
				}
				// add image string to it
				//TODO need fixed this
				/*
				if (name.equals(Constants.CX_INFO_STR)) {
					if (o instanceof UserCXInfo) {
						int imageId = ((UserCXInfo) o).getImageId();
						Element imageIdElement = cxinfo.addElement("imageId");
						imageIdElement.setText(String.valueOf(imageId));
						returnValue
								.append(ImageUtil.generateImageNode(imageId, cxinfo));
						// returnValue.append("<image>");
						// returnValue.append(ImageUtil.readImage(imageId));
						// returnValue.append("</image>");
					}
				}
				if (!name.equals("")) {
					// returnValue.append("</" + name + ">");
				}
				*/
			} else {
				cxinfos.addElement(name);
			}
		} else {
			throw new ClassCastException("Class " + o.getClass().getName()
					+ " is not compatible with " + sourceClass.getName());
		}
		return returnValue.toString();
	}

	@SuppressWarnings("unused")
  private String getProp(Object o, PropertyDescriptor pd, Element cxinfo)
			throws Exception {
		StringBuffer propValue = new StringBuffer("");
		Method m = pd.getReadMethod();
		Element node = null;
		if (checkMethod(m)) {
			Object ret = m.invoke(o);
			if (null == ret) {
				// propValue.append("<" + pd.getName() + "/>");
				cxinfo.addElement(pd.getName());
			} else {
				// propValue.append("<" + pd.getName() + ">");
				String s = pd.getName();
				System.out.println("pd name is  = " + pd.getName());
				node = cxinfo.addElement(pd.getName());
				if (m.getName().equals("getImage")) {
					node.setText(convertBlobToString(ret));
//					propValue.append(convertBlobToString(ret));
				} else {
					if (!checkNull(ret)) {
						// propValue.append(ret.toString());
						node.setText(ret.toString());
					}
				}
				// propValue.append("</" + pd.getName() + ">");
			}
			// return propValue.toString();
		}
		return propValue.toString();
	}

	private boolean checkMethod(Method m) {
		System.out.println(m.getName());
		String[] methods = { "getClass", "getUserInfoId", "getImsi",
				"getAddTime" };
		for (int i = 0; i < methods.length; i++) {
			if (m.getName().equals(methods[i])) {
				return false;
			}
		}
		return true;
	}

	private String convertBlobToString(Object ret) {
		if (ret instanceof Blob) {
			Blob blob = (Blob) ret;
			try {
				// System.out.println("blob size = " +
				// blob.getBinaryStream().available());
				return convertStreamToString(blob.getBinaryStream());
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return "";

	}

	private String convertStreamToString(InputStream is) {
		byte[] b;
		try {
			b = new byte[is.available()];
			is.read(b);
			return Base64Encoder.encodeToString(b, Base64Encoder.DEFAULT);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public boolean checkNull(Object ret) {
		if (ret == null || ret.equals("") || ret.equals("null")) {
			return true;
		}
		return false;
	}
}
