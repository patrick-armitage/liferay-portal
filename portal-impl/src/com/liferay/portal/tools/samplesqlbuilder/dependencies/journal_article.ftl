<#list 1..maxJournalArticlePageCount as journalArticlePageCount>
	<#assign portletIdPrefix = "56_INSTANCE_TEST_" + journalArticlePageCount + "_">

	<#assign layoutModel = dataFactory.newLayoutModel(groupId, groupId + "_journal_article_" + journalArticlePageCount, "", dataFactory.getJournalArticleLayoutColumn(portletIdPrefix))>

	${writerLayoutCSV.write(layoutModel.friendlyURL + "\n")}

	<@insertLayout
		_layoutModel = layoutModel
	/>

	<#assign portletPreferencesModels = dataFactory.newPortletPreferencesModels(layoutModel.plid)>

	<#list portletPreferencesModels as portletPreferencesModel>
		<@insertPortletPreferences
			_portletPreferencesModel = portletPreferencesModel
		/>
	</#list>

	<#list 1..maxJournalArticleCount as journalArticleCount>
		<#assign journalArticleResourceModel = dataFactory.newJournalArticleResourceModel(groupId)>

		insert into JournalArticleResource values ('${journalArticleResourceModel.uuid}', ${journalArticleResourceModel.resourcePrimKey}, ${journalArticleResourceModel.groupId}, '${journalArticleResourceModel.articleId}');

		<#list 1..maxJournalArticleVersionCount as versionCount>
			<#assign journalArticleModel = dataFactory.newJournalArticleModel(journalArticleResourceModel, journalArticleCount, versionCount)>

			insert into JournalArticle values ('${journalArticleModel.uuid}', ${journalArticleModel.id}, ${journalArticleModel.resourcePrimKey}, ${journalArticleModel.groupId}, ${journalArticleModel.companyId}, ${journalArticleModel.userId}, '${journalArticleModel.userName}', '${dataFactory.getDateString(journalArticleModel.createDate)}', '${dataFactory.getDateString(journalArticleModel.modifiedDate)}', ${journalArticleModel.folderId}, ${journalArticleModel.classNameId}, ${journalArticleModel.classPK}, '${journalArticleModel.articleId}', ${journalArticleModel.version}, '${journalArticleModel.title}', '${journalArticleModel.urlTitle}', '${journalArticleModel.description}', '${journalArticleModel.content}', '${journalArticleModel.type}', '${journalArticleModel.structureId}', '${journalArticleModel.templateId}', '${journalArticleModel.layoutUuid}', '${dataFactory.getDateString(journalArticleModel.displayDate)}', '${dataFactory.getDateString(journalArticleModel.expirationDate)}', '${dataFactory.getDateString(journalArticleModel.reviewDate)}', ${journalArticleModel.indexable?string}, ${journalArticleModel.smallImage?string}, ${journalArticleModel.smallImageId}, '${journalArticleModel.smallImageURL}', ${journalArticleModel.status}, ${journalArticleModel.statusByUserId}, '${journalArticleModel.statusByUserName}', '${dataFactory.getDateString(journalArticleModel.statusDate)}');

			<@insertSocialActivity
				_entry = journalArticleModel
			/>

			<#if (versionCount = maxJournalArticleVersionCount) >
				<@insertAssetEntry
					_entry = journalArticleModel
					_categoryAndTag = true
				/>
			</#if>
		</#list>

		<@insertResourcePermissions
			_entry = journalArticleResourceModel
		/>

		<@insertMBDiscussion
			_classNameId = dataFactory.journalArticleClassNameId
			_classPK = journalArticleResourceModel.resourcePrimKey
			_groupId = groupId
			_maxCommentCount = 0
			_mbRootMessageId = counter.get()
			_mbThreadId = counter.get()
		/>

		<#assign portletPreferencesModel = dataFactory.newPortletPreferencesModel(layoutModel.plid, portletIdPrefix + journalArticleCount, journalArticleResourceModel)>

		<@insertPortletPreferences
			_portletPreferencesModel = portletPreferencesModel
		/>

		<#assign journalContentSearchModel = dataFactory.newJournalContentSearchModel(journalArticleModel, layoutModel.plid)>

		insert into JournalContentSearch values (${journalContentSearchModel.contentSearchId}, ${journalContentSearchModel.groupId}, ${journalContentSearchModel.companyId}, ${journalContentSearchModel.privateLayout?string}, ${journalContentSearchModel.layoutId}, '${journalContentSearchModel.portletId}', '${journalContentSearchModel.articleId}');
	</#list>
</#list>