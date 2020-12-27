package utils;

import lombok.extern.slf4j.Slf4j;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Map;
import java.util.Random;


/**
 * 随机工具类
 *
 * @author lizilin
 */
@Slf4j
public class RandomUtils {

    /**
     * 获取一个列表中的随机对象
     *
     * @param objectList
     * @return
     */
    public static String getRandomResult(List<String> objectList) {
        if (CollectionUtils.isEmpty(objectList)) {
            return null;
        }
        Random random = new Random();
        return objectList.get(random.nextInt(objectList.size()));
    }

    /**
     * 根据权重概率性获取随机对象
     * @param probabilityMap
     * @return
     */
//    public static Object getResultByProbability(Map<String, Float> probabilityMap) {
//        Float totalFloat = 0f;
//        for (String key : probabilityMap.keySet()) {
//            if (probabilityMap.get(key) <= 1) {
//                totalFloat += probabilityMap.get(key);
//                if (totalFloat > 0) {
//                    log.error("Random utils error:error probability!");
//                    return null;
//                }
//            } else {
//                log.error("Random utils error:error probability!");
//                return null;
//            }
//        }
//    }


}
