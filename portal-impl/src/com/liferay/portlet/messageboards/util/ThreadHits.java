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

package com.liferay.portlet.messageboards.util;

import com.liferay.portal.kernel.search.Document;
import com.liferay.portal.kernel.search.Hits;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.util.Time;
import com.liferay.util.lucene.HitsImpl;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * <a href="ThreadHits.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 *
 */
public class ThreadHits extends HitsImpl {

	public ThreadHits() {
		super();
	}

	public void recordHits(Hits hits) throws Exception {
		setSearcher(((HitsImpl)hits).getSearcher());

		Set<Long> threadIds = new HashSet<Long>();

		List<Document> docs = new ArrayList<Document>(hits.getLength());
		List<Float> scores = new ArrayList<Float>(hits.getLength());

		for (int i = 0; i < hits.getLength(); i++) {
			Document doc = hits.doc(i);

			Long threadId = GetterUtil.getLong(doc.get("threadId"));

			if (!threadIds.contains(threadId)) {
				threadIds.add(threadId);

				docs.add(hits.doc(i));
				scores.add(new Float(hits.score(i)));
			}
		}

		setLength(docs.size());
		setDocs(docs.toArray(new Document[docs.size()]));
		setScores(scores.toArray(new Float[scores.size()]));

		setSearchTime(
			(float)(System.currentTimeMillis() - getStart()) / Time.SECOND);
	}

}