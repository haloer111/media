package com.aojing.redstore.media.util;

import org.apache.commons.lang3.RandomUtils;

import java.util.UUID;

/**
 * 生成策略 : UUID32位随机数+当前毫秒数+随机数1位+当前项目名称
 *
 * @author gexiao
 * @date 2018/12/6 14:20
 */
public class KeyUtil {
    private final static String projectname = System.getProperty("user.dir");
    //windows
    private final static String PN_WINDOWS = projectname.substring(projectname.lastIndexOf('\\') + 1,
            projectname.length());
    //linux
    private final static String PN_LINUX = projectname.substring(projectname.lastIndexOf('/') + 1,
            projectname.length());

    // 生成策略 : UUID位数随机数+当前毫秒数+随机数1位+当前项目名称


    public static void main(String[] args) {
        System.out.println(UUID.randomUUID());
        System.out.println(get8UUID());
        System.out.println(get12UUID());
        System.out.println(get16UUID());
        System.out.println(get20UUID());
        System.out.println(get24UUID());
        System.out.println(get32UUID());
        String projectname = System.getProperty("user.dir");
        String pn = projectname.substring(projectname.lastIndexOf('\\') + 1, projectname.length());
        System.out.println(pn);
        System.out.println(getGoodCode());
    }

    /**
     * 获得4个长度的十六进制的UUID+当前毫秒数+随机数1位+当前项目名称
     *
     * @return UUID
     */
    public static String get4UUID() {
        UUID id = UUID.randomUUID();
        String[] idd = id.toString().split("-");
        return new StringBuilder().append(idd[1]).append(System.currentTimeMillis()).append(RandomUtils.nextInt(1, 9)).append(PN_WINDOWS).toString();
    }

    /**
     * 获得8个长度的十六进制的UUID+当前毫秒数+随机数1位+当前项目名称
     *
     * @return UUID
     */
    public static String get8UUID() {
        UUID id = UUID.randomUUID();
        String[] idd = id.toString().split("-");
        return new StringBuilder().append(idd[0]).append(System.currentTimeMillis()).append(RandomUtils.nextInt(1, 9)).append(PN_WINDOWS).toString();

    }

    /**
     * 获得12个长度的十六进制的UUID+当前毫秒数+随机数1位+当前项目名称
     *
     * @return UUID
     */
    public static String get12UUID() {
        UUID id = UUID.randomUUID();
        String[] idd = id.toString().split("-");
        return new StringBuilder().append(idd[0]).append(idd[1]).append(System.currentTimeMillis()).append(RandomUtils.nextInt(1, 9)).append(PN_WINDOWS).toString();

    }

    /**
     * 获得16个长度的十六进制的UUID+当前毫秒数+随机数1位+当前项目名称
     *
     * @return UUID
     */
    public static String get16UUID() {

        UUID id = UUID.randomUUID();
        String[] idd = id.toString().split("-");
        return new StringBuilder().append(idd[0]).append(idd[1]).append(idd[2]).append(System.currentTimeMillis()).append(RandomUtils.nextInt(1, 9)).append(PN_WINDOWS).toString();

    }

    /**
     * 获得20个长度的十六进制的UUID+当前毫秒数+随机数1位+当前项目名称
     *
     * @return UUID
     */
    public static String get20UUID() {

        UUID id = UUID.randomUUID();
        String[] idd = id.toString().split("-");
        return new StringBuilder().append(idd[0]).append(idd[1]).append(idd[2]).append(idd[3]).append(System.currentTimeMillis()).append(RandomUtils.nextInt(1, 9)).append(PN_WINDOWS).toString();

    }

    /**
     * 获得24个长度的十六进制的UUID+当前毫秒数+随机数1位+当前项目名称
     *
     * @return UUID
     */
    public static String get24UUID() {
        UUID id = UUID.randomUUID();
        String[] idd = id.toString().split("-");
        return new StringBuilder().append(idd[0]).append(idd[1]).append(idd[4]).append(System.currentTimeMillis()).append(RandomUtils.nextInt(1, 9)).append(PN_WINDOWS).toString();

    }

    /**
     * 获得32个长度的十六进制的UUID+当前毫秒数+随机数1位+当前项目名称
     *
     * @return UUID
     */
    public static String get32UUID() {
        UUID id = UUID.randomUUID();
        String[] idd = id.toString().split("-");
        return new StringBuilder().append(idd[0]).append(idd[1]).append(idd[2]).append(idd[3]).append(idd[4]).append(System.currentTimeMillis()).append(RandomUtils.nextInt(1, 9)).append(PN_WINDOWS).toString();

    }


    /**
     * 获得商品编码5位随机数+当前毫秒
     *
     * @return UUID
     */
    public static String getGoodCode() {
        return new StringBuilder().append(RandomUtils.nextInt(0, 89999) + 10000).append(System.currentTimeMillis()).toString();
    }


}
