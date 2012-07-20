package com.server.cx.service.cx.impl;

import com.server.cx.dao.cx.*;
import com.server.cx.entity.cx.CXInfo;
import com.server.cx.entity.cx.Signature;
import com.server.cx.exception.SystemException;
import com.server.cx.service.cx.CXInfoManagerService;
import com.server.cx.util.FileUtil;
import com.server.cx.util.business.CXInfoType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service("cxInfoManagerService")
public class CXInfoManagerServiceImpl implements CXInfoManagerService {
	
    @Autowired
	private CXInfoDao cxInfoDao;
    @Autowired
    private GenericSignatureDao genericSignatureDao;
    @Autowired
    private GenericCXInfoDao genericCXInfoDao;
    
    @Autowired
	private SignatureDao signatureDao;
	public CXInfoManagerServiceImpl() {
	}

    @Override
    public List<CXInfo> browserAllData(Map<String, String> paramsMap) throws SystemException {
        List<CXInfo> resultList = cxInfoDao.browserAllData(paramsMap);        
        return resultList;
    }
    
    /**
     * 添加新的彩像记录
     * <p>If necessary, describe how it does and how to use it.</P>
     * @param serverPath
     * @param cxInfo
     * @throws com.server.cx.exception.SystemException
     */
    public void addNewCXInfo(final String serverPath, CXInfo cxInfo) throws SystemException {
        String fileData = cxInfo.getFileData();
        String fileName = cxInfo.getFileName();
        String fileType = cxInfo.getFileType();
        String type = null;
        if(cxInfo.getType() == null ||  "".equals( cxInfo.getType())){
            if("jpg".equalsIgnoreCase(fileType) || "png".equalsIgnoreCase(fileType)){
                type = CXInfoType.PICTURE.getValue();
            } else if("3gp".equalsIgnoreCase(fileType) || "mp4".equalsIgnoreCase(fileType)){
                type = CXInfoType.VIDEO.getValue();
            } else if("gif".equalsIgnoreCase(fileType)){
                type = CXInfoType.AMINATION.getValue();
            }
            cxInfo.setType(type);
        }
        
        String filePath = "";
        String thumbnailFilePath = "";
        Map<String,String> resultMap;
        if(fileData != null && !"".equals(fileData)){
            FileUtil fileUtil = new FileUtil();
            resultMap = fileUtil.storeCXFileData(fileName, fileType.toLowerCase(), fileData, serverPath);
            filePath = resultMap.get("filePath");
            thumbnailFilePath = resultMap.get("thumbnailFilePath");
        }
        if(thumbnailFilePath !=null && !"".equals(thumbnailFilePath) && !"3gp".equals(fileType)&&!"mp4".equals(fileType)&&!"gif".equals(fileType)){
           // filePath = thumbnailFilePath;
        }
        if(thumbnailFilePath == null || "".equals(thumbnailFilePath)){
            thumbnailFilePath = filePath;
        }
        cxInfo.setFileData(null);
        cxInfo.setPath(filePath);
        cxInfo.setThumbnailPath(thumbnailFilePath);
        //TODO WORKAROUND 级联添加
        if(cxInfo.getSignature() == null){
            Signature signature = genericSignatureDao.findOne(1L);
            cxInfo.setSignature(signature);
        }else if(cxInfo.getSignature().getId() == null){
            Signature tempSignature = signatureDao.findSignatureByContent(cxInfo.getSignature().getContent());
            if(tempSignature == null){
                genericSignatureDao.save(cxInfo.getSignature());
            }else{
                cxInfo.setSignature(tempSignature);
            }
        }
        genericCXInfoDao.save(cxInfo);
    }
}
