package io.github.paradoxicalblock.storycraft.util;

import com.google.common.base.Charsets;
import io.github.paradoxicalblock.storycraft.main.StoryCraft;
import net.minecraft.util.ChatUtil;
import org.apache.commons.io.IOUtils;

import java.io.IOException;
import java.io.InputStream;
import java.util.*;

public class Localizer {
    private Map<String, String> localizerMap = new HashMap<String, String>();
    private static final ArrayList<String> EMPTY_LIST = new ArrayList<>();

    public Localizer() {
        InputStream inStream = ChatUtil.class.getResourceAsStream("/assets/storycraft/lang/en_us.json");

        try {
            List<String> lines = IOUtils.readLines(inStream, Charsets.UTF_8);

            for (String line : lines) {
                if (line.startsWith("#") || line.isEmpty()) {
                    continue;
                }

                String[] split = line.split("\\=");
                String key = split[0];
                String value = split[1];

                localizerMap.put(key, value);
            }
        } catch (IOException e) {
            StoryCraft.LOGGER.error("Error initializing localizer: " + e);
        }
    }

    public String localize(String key, String... vars) {
        ArrayList<String> list = new ArrayList<>();
        Collections.addAll(list, vars);
        return localize(key, list);
    }

    public String localize(String key, ArrayList<String> vars) {
        String result = localizerMap.getOrDefault(key, key);
        if (result.equals(key)) {
            List<String> responses = new ArrayList<>();
            for (String value : localizerMap.keySet()) {
                if (value.contains(key)) {
                    responses.add(localizerMap.get(value).replace("\\", ""));
                }
            }

            if (responses.size() > 0) {
                result = responses.get(new Random().nextInt(responses.size()));
            }
        }

        return parseVars(result, vars).replaceAll("\\\\", "");
    }

    private String parseVars(String str, ArrayList<String> vars) {
        int index = 1;

        String varString = "%v" + index + "%";
        while (str.contains("%v") && index < 10) { // signature of a var being present
            try {
                str = str.replaceAll(varString, vars.get(index - 1));
            } catch (IndexOutOfBoundsException e) {
                str = str.replaceAll(varString, "");
                StoryCraft.LOGGER.warn("Failed to replace variable in localized string: " + str);
            } finally {
                index++;
                varString = "%v" + index + "%";
            }
        }

        return str;
    }
}