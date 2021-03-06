// $Id: CssSpeak.java,v 1.2 2012-12-18 09:56:16 ylafon Exp $
// Author: Yves Lafon <ylafon@w3.org>
//
// (c) COPYRIGHT MIT, ERCIM and Keio University, 2012.
// Please first read the full copyright statement in file COPYRIGHT.html
package org.w3c.css.properties.css3;

import org.w3c.css.util.ApplContext;
import org.w3c.css.util.InvalidParamException;
import org.w3c.css.values.CssExpression;
import org.w3c.css.values.CssIdent;
import org.w3c.css.values.CssTypes;
import org.w3c.css.values.CssValue;

/**
 * @pec http://www.w3.org/TR/2012/CR-css3-speech-20120320/#speaking-props-speak
 */
public class CssSpeak extends org.w3c.css.properties.css.CssSpeak {

	public static final CssIdent[] allowed_values;

	static {
		String[] _allowed_values = {"normal", "none", "auto"};
		int i = 0;
		allowed_values = new CssIdent[_allowed_values.length];
		for (String s : _allowed_values) {
			allowed_values[i++] = CssIdent.getIdent(s);
		}
	}

	public static final CssIdent getAllowedIdent(CssIdent ident) {
		for (CssIdent id : allowed_values) {
			if (id.equals(ident)) {
				return id;
			}
		}
		return null;
	}

	/**
	 * Create a new CssSpeak
	 */
	public CssSpeak() {
		value = initial;
	}

	/**
	 * Creates a new CssSpeak
	 *
	 * @param expression The expression for this property
	 * @throws org.w3c.css.util.InvalidParamException
	 *          Expressions are incorrect
	 */
	public CssSpeak(ApplContext ac, CssExpression expression, boolean check)
			throws InvalidParamException {
		if (check && expression.getCount() > 1) {
			throw new InvalidParamException("unrecognize", ac);
		}
		setByUser();

		CssValue val;
		val = expression.getValue();
		expression.getOperator();

		switch (val.getType()) {
			case CssTypes.CSS_IDENT:
				CssIdent id = (CssIdent) val;
				if (inherit.equals(id)) {
					value = inherit;
					break;
				} else {
					value = getAllowedIdent(id);
					if (value != null) {
						break;
					}
				}
			default:
				throw new InvalidParamException("value",
						val.toString(),
						getPropertyName(), ac);
		}
		expression.next();
	}

	public CssSpeak(ApplContext ac, CssExpression expression)
			throws InvalidParamException {
		this(ac, expression, false);
	}

}

