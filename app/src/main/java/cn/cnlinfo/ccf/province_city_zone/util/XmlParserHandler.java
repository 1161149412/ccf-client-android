package cn.cnlinfo.ccf.province_city_zone.util;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import java.util.ArrayList;
import java.util.List;

import cn.cnlinfo.ccf.province_city_zone.entity.City;
import cn.cnlinfo.ccf.province_city_zone.entity.Districts;
import cn.cnlinfo.ccf.province_city_zone.entity.Province;

/**
 * Created by Administrator on 2018/2/5 0005.
 */

public class XmlParserHandler extends DefaultHandler {
    private List<Province> provinceList = new ArrayList();
    private Province province = new Province();
    private City city = new City();
    private Districts districts = new  Districts();
    public XmlParserHandler() {

    }

    public List<Province> getProvinceDataList() {
        return this.provinceList;
    }

    public void startDocument() throws SAXException {
        //Log.d("TAG","startDocument\n");
    }


    @Override
    public void endDocument() throws SAXException {
        super.endDocument();
       // Log.d("TAG","endDocument\n");
    }

    public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
        if(qName.equals("Province")) {
            this.province = new Province();
            this.province.setId(attributes.getValue(0));
            this.province.setProvinceName(attributes.getValue(1));
            this.province.setCityList(new ArrayList<City>());
        }else if(qName.equals("City")){
            this.city = new City();
            this.city.setCityId(attributes.getValue(0));
            this.city.setCityName(attributes.getValue(1));
            this.city.setpId(attributes.getValue(2));
            this.city.setZipCode(attributes.getValue(3));
            this.city.setDistrictsList(new ArrayList<Districts>());
        }else if(qName.equals("District")){
            this.districts = new Districts();
            this.districts.setDistrictsId(attributes.getValue(0));
            this.districts.setDistrictsName(attributes.getValue(1));
            this.districts.setcId(attributes.getValue(2));
        }
    }

    /**
     *
     * @param uri
     * @param localName 标签名
     * @param qName 标签名
     * @throws SAXException
     */
    public void endElement(String uri, String localName, String qName) throws SAXException {
        if (qName.equals("Province")){
            this.provinceList.add(this.province);
        }else if (qName.equals("City")){
            this.province.getCityList().add(this.city);
        }else if (qName.equals("District")){
            this.city.getDistrictsList().add(this.districts);
        }
    }

    /**
     * 标签值
     * @param ch
     * @param start
     * @param length
     * @throws SAXException
     */
    public void characters(char[] ch, int start, int length) throws SAXException {
       /* Log.d("TAG",new String(ch,start,length));
        Log.d("TAG","-------------------------");*/
    }
}
