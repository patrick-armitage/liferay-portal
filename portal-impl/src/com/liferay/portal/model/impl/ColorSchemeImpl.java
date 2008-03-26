/**
 * Copyright (c) 2000-2008 Liferay, Inc. All rights reserved.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package com.liferay.portal.model.impl;

import com.liferay.portal.kernel.util.PropertiesUtil;
import com.liferay.portal.kernel.util.SafeProperties;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.model.ColorScheme;
import com.liferay.portal.util.PropsValues;

import java.io.IOException;

import java.util.Properties;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * <a href="ColorSchemeImpl.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 *
 */
public class ColorSchemeImpl implements ColorScheme {

	public static final String BODY_BG = "body-bg";

	public static final String LAYOUT_BG = "layout-bg";

	public static final String LAYOUT_TEXT = "layout-text";

	public static final String LAYOUT_TAB_BG = "layout-tab-bg";

	public static final String LAYOUT_TAB_TEXT = "layout-tab-text";

	public static final String LAYOUT_TAB_SELECTED_BG =
		"layout-tab-selected-bg";

	public static final String LAYOUT_TAB_SELECTED_TEXT =
		"layout-tab-selected-text";

	public static final String PORTLET_TITLE_BG = "portlet-title-bg";

	public static final String PORTLET_TITLE_TEXT = "portlet-title-text";

	public static final String PORTLET_MENU_BG = "portlet-menu-bg";

	public static final String PORTLET_MENU_TEXT = "portlet-menu-text";

	public static final String PORTLET_BG = "portlet-bg";

	public static final String PORTLET_FONT = "portlet-font";

	public static final String PORTLET_FONT_DIM = "portlet-font-dim";

	public static final String PORTLET_MSG_STATUS = "portlet-msg-status";

	public static final String PORTLET_MSG_INFO = "portlet-msg-info";

	public static final String PORTLET_MSG_ERROR = "portlet-msg-error";

	public static final String PORTLET_MSG_ALERT = "portlet-msg-alert";

	public static final String PORTLET_MSG_SUCCESS = "portlet-msg-success";

	public static final String PORTLET_SECTION_HEADER =
		"portlet-section-header";

	public static final String PORTLET_SECTION_HEADER_BG =
		"portlet-section-header-bg";

	public static final String PORTLET_SECTION_SUBHEADER =
		"portlet-section-subheader";

	public static final String PORTLET_SECTION_SUBHEADER_BG =
		"portlet-section-subheader-bg";

	public static final String PORTLET_SECTION_BODY = "portlet-section-body";

	public static final String PORTLET_SECTION_BODY_BG =
		"portlet-section-body-bg";

	public static final String PORTLET_SECTION_BODY_HOVER =
		"portlet-section-body-hover";

	public static final String PORTLET_SECTION_BODY_HOVER_BG =
		"portlet-section-body-hover-bg";

	public static final String PORTLET_SECTION_ALTERNATE =
		"portlet-section-alternate";

	public static final String PORTLET_SECTION_ALTERNATE_BG =
		"portlet-section-alternate-bg";

	public static final String PORTLET_SECTION_ALTERNATE_HOVER =
		"portlet-section-alternate-hover";

	public static final String PORTLET_SECTION_ALTERNATE_HOVER_BG =
		"portlet-section-alternate-hover-bg";

	public static final String PORTLET_SECTION_SELECTED =
		"portlet-section-selected";

	public static final String PORTLET_SECTION_SELECTED_BG =
		"portlet-section-selected-bg";

	public static final String PORTLET_SECTION_SELECTED_HOVER =
		"portlet-section-selected-hover";

	public static final String PORTLET_SECTION_SELECTED_HOVER_BG =
		"portlet-section-selected-hover-bg";

	public static String getDefaultRegularColorSchemeId() {
		return PropsValues.DEFAULT_REGULAR_COLOR_SCHEME_ID;
	}

	public static String getDefaultWapColorSchemeId() {
		return PropsValues.DEFAULT_WAP_COLOR_SCHEME_ID;
	}

	public static ColorScheme getNullColorScheme() {
		return new ColorSchemeImpl(
			getDefaultRegularColorSchemeId(), StringPool.BLANK,
			StringPool.BLANK);
	}

	public ColorSchemeImpl() {
	}

	public ColorSchemeImpl(String colorSchemeId) {
		_colorSchemeId = colorSchemeId;
	}

	public ColorSchemeImpl(String colorSchemeId, String name, String cssClass) {
		_colorSchemeId = colorSchemeId;
		_name = name;
		_cssClass = cssClass;
	}

	public String getColorSchemeId() {
		return _colorSchemeId;
	}

	public String getName() {
		if (Validator.isNull(_name)) {
			return _colorSchemeId;
		}
		else {
			return _name;
		}
	}

	public void setName(String name) {
		_name = name;
	}

	public boolean getDefaultCs() {
		return _defaultCs;
	}

	public boolean isDefaultCs() {
		return _defaultCs;
	}

	public void setDefaultCs(boolean defaultCs) {
		_defaultCs = defaultCs;
	}

	public String getCssClass() {
		return _cssClass;
	}

	public void setCssClass(String cssClass) {
		_cssClass = cssClass;
	}

	public String getColorSchemeImagesPath() {
		return _colorSchemeImagesPath;
	}

	public String getColorSchemeThumbnailPath() {

		// LEP-5270

		if (Validator.isNotNull(_cssClass) &&
			Validator.isNotNull(_colorSchemeImagesPath)) {

			int pos = _cssClass.indexOf(StringPool.SPACE);

			if (pos > 0) {
				if (_colorSchemeImagesPath.endsWith(
						_cssClass.substring(0, pos))) {

					String subclassPath = StringUtil.replace(
						_cssClass, StringPool.SPACE, StringPool.SLASH);

					return _colorSchemeImagesPath + subclassPath.substring(pos);
				}
			}
		}

		return _colorSchemeImagesPath;
	}

	public void setColorSchemeImagesPath(String colorSchemeImagesPath) {
		_colorSchemeImagesPath = colorSchemeImagesPath;
	}

	public String getSettings() {
		return PropertiesUtil.toString(_settingsProperties);
	}

	public void setSettings(String settings) {
		_settingsProperties.clear();

		try {
			PropertiesUtil.load(_settingsProperties, settings);
			PropertiesUtil.trimKeys(_settingsProperties);
		}
		catch (IOException ioe) {
			_log.error(ioe);
		}
	}

	public Properties getSettingsProperties() {
		return _settingsProperties;
	}

	public void setSettingsProperties(Properties settingsProperties) {
		_settingsProperties = settingsProperties;
	}

	public String getSetting(String key) {
		//return _settingsProperties.getProperty(key);

		// FIX ME

		if (key.endsWith("-bg")) {
			return "#FFFFFF";
		}
		else {
			return "#000000";
		}
	}

	public String getBodyBg() {
		return getSetting(BODY_BG);
	}

	public String getLayoutBg() {
		return getSetting(LAYOUT_BG);
	}

	public String getLayoutText() {
		return getSetting(LAYOUT_TEXT);
	}

	public String getLayoutTabBg() {
		return getSetting(LAYOUT_TAB_BG);
	}

	public String getLayoutTabText() {
		return getSetting(LAYOUT_TAB_TEXT);
	}

	public String getLayoutTabSelectedBg() {
		return getSetting(LAYOUT_TAB_SELECTED_BG);
	}

	public String getLayoutTabSelectedText() {
		return getSetting(LAYOUT_TAB_SELECTED_TEXT);
	}

	public String getPortletTitleBg() {
		return getSetting(PORTLET_TITLE_BG);
	}

	public String getPortletTitleText() {
		return getSetting(PORTLET_TITLE_TEXT);
	}

	public String getPortletMenuBg() {
		return getSetting(PORTLET_MENU_BG);
	}

	public String getPortletMenuText() {
		return getSetting(PORTLET_MENU_TEXT);
	}

	public String getPortletBg() {
		return getSetting(PORTLET_BG);
	}

	public String getPortletFont() {
		return getSetting(PORTLET_FONT);
	}

	public String getPortletFontDim() {
		return getSetting(PORTLET_FONT_DIM);
	}

	public String getPortletMsgStatus() {
		return getSetting(PORTLET_MSG_STATUS);
	}

	public String getPortletMsgInfo() {
		return getSetting(PORTLET_MSG_INFO);
	}

	public String getPortletMsgError() {
		return getSetting(PORTLET_MSG_ERROR);
	}

	public String getPortletMsgAlert() {
		return getSetting(PORTLET_MSG_ALERT);
	}

	public String getPortletMsgSuccess() {
		return getSetting(PORTLET_MSG_SUCCESS);
	}

	public String getPortletSectionHeader() {
		return getSetting(PORTLET_SECTION_HEADER);
	}

	public String getPortletSectionHeaderBg() {
		return getSetting(PORTLET_SECTION_HEADER_BG);
	}

	public String getPortletSectionSubheader() {
		return getSetting(PORTLET_SECTION_SUBHEADER);
	}

	public String getPortletSectionSubheaderBg() {
		return getSetting(PORTLET_SECTION_SUBHEADER_BG);
	}

	public String getPortletSectionBody() {
		return getSetting(PORTLET_SECTION_BODY);
	}

	public String getPortletSectionBodyBg() {
		return getSetting(PORTLET_SECTION_BODY_BG);
	}

	public String getPortletSectionBodyHover() {
		return getSetting(PORTLET_SECTION_BODY_HOVER);
	}

	public String getPortletSectionBodyHoverBg() {
		return getSetting(PORTLET_SECTION_BODY_HOVER_BG);
	}

	public String getPortletSectionAlternate() {
		return getSetting(PORTLET_SECTION_ALTERNATE);
	}

	public String getPortletSectionAlternateBg() {
		return getSetting(PORTLET_SECTION_ALTERNATE_BG);
	}

	public String getPortletSectionAlternateHover() {
		return getSetting(PORTLET_SECTION_ALTERNATE_HOVER);
	}

	public String getPortletSectionAlternateHoverBg() {
		return getSetting(PORTLET_SECTION_ALTERNATE_HOVER_BG);
	}

	public String getPortletSectionSelected() {
		return getSetting(PORTLET_SECTION_SELECTED);
	}

	public String getPortletSectionSelectedBg() {
		return getSetting(PORTLET_SECTION_SELECTED_BG);
	}

	public String getPortletSectionSelectedHover() {
		return getSetting(PORTLET_SECTION_SELECTED_HOVER);
	}

	public String getPortletSectionSelectedHoverBg() {
		return getSetting(PORTLET_SECTION_SELECTED_HOVER_BG);
	}

	public int compareTo(ColorScheme colorScheme) {
		return getName().compareTo(colorScheme.getName());
	}

	public boolean equals(Object obj) {
		if (obj == null) {
			return false;
		}

		ColorScheme colorScheme = null;

		try {
			colorScheme = (ColorScheme)obj;
		}
		catch (ClassCastException cce) {
			return false;
		}

		String colorSchemeId = colorScheme.getColorSchemeId();

		if (getColorSchemeId().equals(colorSchemeId)) {
			return true;
		}
		else {
			return false;
		}
	}

	private static Log _log = LogFactory.getLog(ColorScheme.class);

	private String _colorSchemeId;
	private String _name;
	private String _cssClass;
	private String _colorSchemeImagesPath =
		"${images-path}/color_schemes/${css-class}";
	private boolean _defaultCs;
	private Properties _settingsProperties = new SafeProperties();

}