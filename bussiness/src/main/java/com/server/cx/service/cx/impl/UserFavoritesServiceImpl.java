package com.server.cx.service.cx.impl;

import com.cl.cx.platform.dto.DataItem;
import com.cl.cx.platform.dto.DataPage;
import com.cl.cx.platform.dto.IdDTO;
import com.cl.cx.platform.dto.OperationDescription;
import com.google.common.base.Preconditions;
import com.google.common.collect.Lists;
import com.server.cx.constants.Constants;
import com.server.cx.dao.cx.GraphicInfoDao;
import com.server.cx.dao.cx.UserFavoritesDao;
import com.server.cx.dao.cx.UserInfoDao;
import com.server.cx.dao.cx.spec.UserFavoriteSpecifications;
import com.server.cx.entity.cx.GraphicInfo;
import com.server.cx.entity.cx.UserFavorites;
import com.server.cx.entity.cx.UserInfo;
import com.server.cx.exception.CXServerBusinessException;
import com.server.cx.exception.SystemException;
import com.server.cx.service.cx.UserFavoritesService;
import com.server.cx.service.util.BusinessFunctions;
import com.server.cx.util.RestSender;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.concurrent.ExecutionException;

@Service("userFavoritesService")
@Transactional
public class UserFavoritesServiceImpl extends  BasicService implements UserFavoritesService {

    @Autowired
    private UserInfoDao userInfoDao;

    @Autowired
    private UserFavoritesDao userFavoritesDao;

    @Autowired
    private GraphicInfoDao graphicInfoDao;

    @Autowired
    @Qualifier("cxinfosQueryIdRestSender")
    private RestSender restSender;

    @Autowired
    private BusinessFunctions businessFunctions;

    private String dealResult = "";
    private List<UserFavorites> userFavoritesList;
    private List<String> resourceIdsList;

    @Override
    public OperationDescription addNewUserFavorites(String imsi, IdDTO idDTO) throws SystemException {

        Preconditions.checkNotNull(imsi);
        Preconditions.checkNotNull(idDTO);

        UserInfo userInfo = userInfoDao.findByImsi(imsi);
        Preconditions.checkNotNull(userInfo,"用户不存在");

        String userId = userInfo.getId();
        boolean isAlreadyAddedInUserFavorites = userFavoritesDao.isAlreadAddedInUserFavorites(userId, idDTO.getId());

        if (isAlreadyAddedInUserFavorites) {
            throw new CXServerBusinessException("用户已经收藏该彩像");
        }

        Integer totalCountOfUserFavorites = userFavoritesDao.getUserFavoritesTotalCount(userId);
        if (totalCountOfUserFavorites >= Constants.TOTAL_USERFAVORITES_COUNT) {
            throw new CXServerBusinessException("用户收藏已经超出限制的"+Constants.TOTAL_USERFAVORITES_COUNT + "条!");
        }

        UserFavorites userFavorites = new UserFavorites();
        userFavorites.setUser(userInfo);
        GraphicInfo graphicInfo = graphicInfoDao.findOne(idDTO.getId());
        if(graphicInfo == null){
            throw new CXServerBusinessException("找不到图库资源");
        }
        userFavorites.setGraphicInfo(graphicInfo);
        userFavoritesDao.save(userFavorites);

        OperationDescription operationDescription = new OperationDescription();
        operationDescription.setDealResult("执行成功");
        operationDescription.setActionName("addNewUserFavorites");
        return operationDescription;
    }

    @Override
    public OperationDescription deleteUserFavorites(String imsi, IdDTO idDTO) throws SystemException {

        Preconditions.checkNotNull(imsi,"imsi为空");
        Preconditions.checkNotNull(idDTO,"收藏ID为空");
        if (idDTO.getIds().size()<=0) {
            throw new CXServerBusinessException("收藏ID为空");
        }

        UserInfo userInfo = userInfoDao.getUserInfoByImsi(imsi);
        if (null == userInfo) {
            throw new CXServerBusinessException("用户未注册");
        }

        List<UserFavorites> userFavorites =  Lists.newArrayList(userFavoritesDao.findAll(idDTO.getIds()));
        if(userFavorites !=null && userFavorites.size() >0){
            userFavoritesDao.deleteInBatch(userFavorites);
        }

        OperationDescription operationDescription = new OperationDescription();
        operationDescription.setDealResult("执行成功");
        operationDescription.setActionName("deleteUserFavorites");
        return operationDescription;
    }

    @Override
    public OperationDescription deleteUserFavoritesById(String imsi, String userFavoriteId) throws SystemException {
        Preconditions.checkNotNull(imsi,"imsi为空");
        Preconditions.checkNotNull(userFavoriteId,"收藏ID为空");

        UserInfo userInfo = userInfoDao.getUserInfoByImsi(imsi);
        if (null == userInfo) {
            throw new CXServerBusinessException("用户未注册");
        }

        userFavoritesDao.delete(userFavoriteId);

        OperationDescription operationDescription = new OperationDescription();
        operationDescription.setDealResult("执行成功");
        operationDescription.setActionName("deleteUserFavorites");
        return operationDescription;
    }

    @Override
    public DataPage getAllUserFavorites(final String imsi, Integer offset, Integer limit) throws ExecutionException {
        Preconditions.checkNotNull(imsi);
        Preconditions.checkNotNull(offset);
        Preconditions.checkNotNull(limit);

        UserInfo userInfo = userInfoDao.findByImsi(imsi);
        if (null == userInfo) {
            throw new CXServerBusinessException("用户未注册");
        }
        //TODO need fix 需要一个级联查询实现.
        PageRequest pageRequest = new PageRequest(offset,limit, Sort.Direction.DESC,"createdOn");
        Page page = userFavoritesDao.findAll(UserFavoriteSpecifications.userFavoritesSpecification(userInfo),pageRequest);
        List<UserFavorites> userFavoritesList  =  page.getContent();

        List<DataItem> collectedGraphicInfoItems = transformToDataItemList(imsi, userFavoritesList);

        DataPage dataPage = generateDataPage(imsi, offset, limit, page, collectedGraphicInfoItems);
        return dataPage;

    }

    private List<DataItem> transformToDataItemList(String imsi, List<UserFavorites> userFavoritesList) throws ExecutionException {
        return Lists.transform(userFavoritesList, businessFunctions.userFavoriteTransformToCollectedGraphicInfoItem(imsi));
    }

    private DataPage generateDataPage(String imsi, Integer offset, Integer limit, Page page, List<DataItem> collectedGraphicInfoItems) {
        DataPage dataPage = new DataPage();
        dataPage.setLimit(page.getSize());
        dataPage.setOffset(page.getNumber());
        dataPage.setTotal(page.getTotalPages());
        dataPage.setItems(collectedGraphicInfoItems);
        dataPage.setHref(generateDataPage(imsi, offset, limit));
        if (offset > 0) {
            int previousOffset = offset - 1;
            dataPage.setPrevious(generateDataPage(imsi, previousOffset, limit));
        }
        if (offset + 1 < page.getTotalPages()) {
            int nextOffset = offset + 1;
            dataPage.setNext(generateDataPage(imsi, nextOffset, limit));
        }
        dataPage.setFirst(generateDataPage(imsi, 0, limit));
        dataPage.setLast(generateDataPage(imsi, (dataPage.getTotal() - 1), limit));
        return dataPage;
    }

    private String generateDataPage(String imsi, int offset, Integer limit) {
        return baseHostAddress + restURL + imsi + "/myCollections?offset=" + offset + "&limit=" + limit;
    }


}
