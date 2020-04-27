package org.xfh.dcore.utils;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Random;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * 图片验证码
 * 
 * @version 2018年12月7日
 */
public class ImageCode {
    // 图片的宽度。
    private int           width     = 160;
    // 图片的高度。
    private int           height    = 40;
    // 验证码字符个数
    private int           codeCount = 4;
    // 验证码干扰线数
    private int           lineCount = 20;
    // 验证码
    private String        code      = null;
    // 验证码图片Buffer
    private BufferedImage buffImg   = null;
    private Random        random    = new Random();

    public ImageCode() {
        creatImage();
    }

    /**
     * 设置图片宽高
     * 
     * @param width
     * @param height
     */
    public ImageCode(int width, int height) {
        this.width = width;
        this.height = height;
        creatImage();
    }

    /**
     * 设置图片宽高,字符个数
     * 
     * @param width
     * @param height
     * @param codeCount
     *            验证码字符个数
     */
    public ImageCode(int width, int height, int codeCount) {
        this.width = width;
        this.height = height;
        this.codeCount = codeCount;
        creatImage();
    }

    /**
     * 设置图片宽高,字符个数,干扰线数
     * 
     * @param width
     * @param height
     * @param codeCount
     *            验证码字符个数
     * @param lineCount
     *            验证码干扰线数
     */
    public ImageCode(int width, int height, int codeCount, int lineCount) {
        this.width = width;
        this.height = height;
        this.codeCount = codeCount;
        this.lineCount = lineCount;
        creatImage();
    }

    /**
     * 生成图片
     */
    private void creatImage() {
        int fontWidth = width / codeCount;// 字体的宽度
        int fontHeight = height - 5;// 字体的高度
        int codeY = height - 8;

        // 图像buffer
        buffImg = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        Graphics2D g = buffImg.createGraphics();
        // 设置背景色
        g.setColor(getRandColor(200, 250));
        g.fillRect(0, 0, width, height);

        // 设置字体
        // Font font = getFont(fontHeight);//随机字体
        Font font = new Font("Fixedsys", Font.BOLD, fontHeight);
        g.setFont(font);

        // 设置干扰线
        for (int i = 0; i < lineCount; i++) {
            int xs = random.nextInt(width);
            int ys = random.nextInt(height);
            int xe = xs + random.nextInt(width);
            int ye = ys + random.nextInt(height);
            g.setColor(getRandColor(1, 255));
            g.drawLine(xs, ys, xe, ye);
        }

        // 添加噪点
        float yawpRate = 0.01f;// 噪声率
        int area = (int) (yawpRate * width * height);
        for (int i = 0; i < area; i++) {
            int x = random.nextInt(width);
            int y = random.nextInt(height);

            buffImg.setRGB(x, y, random.nextInt(255));
        }

        String str = randomStr(codeCount);// 得到随机字符
        this.code = str;
        for (int i = 0; i < codeCount; i++) {
            String strRand = str.substring(i, i + 1);
            g.setColor(getRandColor(1, 255));

            /**
             * g.drawString(a,x,y);<br>
             * a为要画出来的东西，x和y表示要画的东西最左侧字符的基线位于此图形上下文坐标系的 (x, y) 位置处
             */
            g.drawString(strRand, i * fontWidth + 3, codeY);
        }

    }

    /**
     * 得到随机字符
     * 
     * @param n
     * @return
     */
    private String randomStr(int n) {
        String str1 = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz1234567890";
        String str2 = "";
        int len = str1.length() - 1;
        double r;
        for (int i = 0; i < n; i++) {
            r = (Math.random()) * len;
            str2 = str2 + str1.charAt((int) r);
        }
        return str2;
    }

    /**
     * 得到随机颜色
     * 
     * @param fc
     * @param bc
     * @return
     */
    private Color getRandColor(int fc, int bc) {// 给定范围获得随机颜色
        if (fc > 255)
            fc = 255;
        if (bc > 255)
            bc = 255;
        int r = fc + random.nextInt(bc - fc);
        int g = fc + random.nextInt(bc - fc);
        int b = fc + random.nextInt(bc - fc);
        return new Color(r, g, b);
    }

    /**
     * 产生随机字体
     */
    @SuppressWarnings("unused")
    private Font getFont(int size) {
        Random random = new Random();
        Font font[] = new Font[5];
        font[0] = new Font("Ravie", Font.PLAIN, size);
        font[1] = new Font("Antique Olive Compact", Font.PLAIN, size);
        font[2] = new Font("Fixedsys", Font.PLAIN, size);
        font[3] = new Font("Wide Latin", Font.PLAIN, size);
        font[4] = new Font("Gill Sans Ultra Bold", Font.PLAIN, size);
        return font[random.nextInt(5)];
    }

    /**
     * 输出验证码图片
     * 
     * @param os
     * @throws IOException
     */
    public void write(OutputStream os) throws IOException {
        try {
            ImageIO.write(buffImg, "png", os);
        } finally {
            if (null != os) {
                os.close();
            }
        }
    }

    /**
     * 获取验证码图片Buffer
     * 
     * @return
     */
    public BufferedImage getBuffImg() {
        return buffImg;
    }

    /**
     * 获取验证码
     * 
     * @return
     */
    public String getCode() {
        return code.toLowerCase();
    }

    // 使用方法
    public void getCode3(HttpServletRequest req, HttpServletResponse response, HttpSession session) throws IOException { // 设置响应的类型格式为图片格式
        response.setContentType("image/jpeg");
        // 禁止图像缓存。
        response.setHeader("Pragma", "no-cache");
        response.setHeader("Cache-Control", "no-cache");
        response.setDateHeader("Expires", 0);

        ImageCode imageCode = new ImageCode(100, 30, 5, 10);
        session.setAttribute("imageCode", imageCode.getCode());
        imageCode.write(response.getOutputStream());
    }

}
