package com.spencerstudios.makefilesize

fun getText(): ArrayList<String> {

    val paras = ArrayList<String>()

    paras.add("Lorem ipsum dolor sit amet, consectetur adipiscing elit. Praesent a nulla sed erat mollis luctus. Aenean felis dui, euismod nec mauris ac, commodo hendrerit est. Maecenas lobortis rhoncus nisl sit amet faucibus. Duis sagittis ex sed sem mattis, ac finibus diam efficitur. Phasellus vitae gravida neque. In vitae nibh porttitor enim vehicula sollicitudin. Proin euismod turpis nec ligula dignissim mollis. Proin laoreet ante ac eros dictum facilisis. Suspendisse non tincidunt erat, ut aliquam orci. Nulla consectetur mollis lectus, a lobortis purus dapibus a. Suspendisse non orci dui. Sed a lorem ut sapien maximus tincidunt eu quis lorem.")
    paras.add("Praesent egestas, arcu at posuere pharetra, orci magna viverra dolor, in posuere leo dui in eros. In egestas pretium orci, ut ornare odio faucibus vel. In a bibendum odio. Mauris convallis tortor et velit ultrices euismod. Phasellus molestie, enim et pellentesque volutpat, mauris mauris tincidunt ante, eu sodales dui lorem at velit. Nullam eu tortor ac risus sollicitudin iaculis. Donec maximus, turpis tempor rutrum tempus, libero eros tincidunt felis, in ornare velit sapien ornare purus. In venenatis leo massa, sed lobortis dui fringilla a.")
    paras.add("Suspendisse sit amet dictum nulla. Cras tempus euismod leo, a suscipit tortor dignissim eget. Fusce aliquet mauris vitae tellus pretium placerat. Sed mollis facilisis nisl, ac imperdiet ipsum auctor eu. Pellentesque velit quam, porttitor ac egestas non, sagittis vitae tortor. Vestibulum vel enim eu libero egestas imperdiet. Nunc hendrerit finibus nisl, a bibendum felis congue vel. Praesent ipsum tellus, eleifend id congue sit amet, malesuada ut urna. Curabitur nec vestibulum ligula. Aliquam cursus blandit justo, non imperdiet eros posuere in. Aliquam libero quam, dapibus nec aliquet vitae, viverra non ipsum.")
    paras.add("Aenean pretium ullamcorper sapien sit amet rutrum. Vestibulum posuere nisi in felis sagittis sodales. Proin lacus augue, dictum ut ipsum pharetra, lacinia pharetra turpis. Proin non mi nec felis convallis viverra. Aliquam eu leo pretium, auctor quam vitae, dictum massa. Cras sagittis purus quis tristique fermentum. In dolor sapien, ornare quis diam id, laoreet consequat urna. Aliquam tristique felis non ante vestibulum, in ultricies neque consequat. Donec convallis mi est, sed commodo nulla dapibus scelerisque. Suspendisse rhoncus quam ipsum, vel pellentesque eros ullamcorper a. Maecenas sed semper elit. Vivamus posuere a mi eget ornare.")
    paras.add("Nunc semper consectetur maximus. Cras facilisis mauris sed metus tincidunt pulvinar. Suspendisse placerat leo dolor, vitae bibendum risus mattis et. Phasellus fermentum arcu eu dui efficitur ultricies. Donec nec eros quis leo maximus porttitor bibendum vel enim. Donec pharetra libero libero, eget hendrerit justo elementum quis. In facilisis ante purus, vel scelerisque augue pretium a. Sed semper, dolor at bibendum porttitor, ipsum risus vestibulum lacus, non cursus lectus eros sit amet sem. Proin placerat sit amet felis sit amet condimentum.")

    paras.shuffle()

    return paras
}

fun sentence(): ArrayList<String> {

    val l = ArrayList<String>()

    l.add("Lorem ipsum dolor sit amet, consectetur adipiscing elit.")
    l.add("Praesent a nulla sed erat mollis luctus.")
    l.add("Aenean felis dui, euismod nec mauris ac, commodo hendrerit est.")
    l.add("Maecenas lobortis rhoncus nisl sit amet faucibus.")
    l.add("Duis sagittis ex sed sem mattis, ac finibus diam efficitur.")
    l.add("Phasellus vitae gravida neque.")
    l.add("In vitae nibh porttitor enim vehicula sollicitudin.")
    l.add("Proin euismod turpis nec ligula dignissim mollis.")
    l.add("Proin laoreet ante ac eros dictum facilisis.")
    l.add("Suspendisse non tincidunt erat, ut aliquam orci.")
    l.add("Nulla consectetur mollis lectus, a lobortis purus dapibus a.")
    l.add("Suspendisse non orci dui.")
    l.add("Sed a lorem ut sapien maximus tincidunt eu quis lorem.")
    l.add("Praesent egestas, arcu at posuere pharetra, orci magna viverra dolor, in posuere leo dui in eros.")
    l.add("In egestas pretium orci, ut ornare odio faucibus vel.")
    l.add("In a bibendum odio.")
    l.add("Mauris convallis tortor et velit ultrices euismod.")
    l.add("Phasellus molestie, enim et pellentesque volutpat, mauris mauris tincidunt ante, eu sodales dui lorem at velit.")
    l.add("Nullam eu tortor ac risus sollicitudin iaculis.")
    l.add("Donec maximus, turpis tempor rutrum tempus, libero eros tincidunt felis, in ornare velit sapien ornare purus.")
    l.add("In venenatis leo massa, sed lobortis dui fringilla a.")
    l.add("Suspendisse sit amet dictum nulla.")
    l.add("Cras tempus euismod leo, a suscipit tortor dignissim eget.")
    l.add("Fusce aliquet mauris vitae tellus pretium placerat.")
    l.add("Sed mollis facilisis nisl, ac imperdiet ipsum auctor eu.")
    l.add("Pellentesque velit quam, porttitor ac egestas non, sagittis vitae tortor.")
    l.add("Vestibulum vel enim eu libero egestas imperdiet.")
    l.add("Nunc hendrerit finibus nisl, a bibendum felis congue vel.")
    l.add("Praesent ipsum tellus, eleifend id congue sit amet, malesuada ut urna.")
    l.add("Curabitur nec vestibulum ligula.")
    l.add("Aliquam cursus blandit justo, non imperdiet eros posuere in.")
    l.add("Aliquam libero quam, dapibus nec aliquet vitae, viverra non ipsum.")
    l.add("Aenean pretium ullamcorper sapien sit amet rutrum.")
    l.add("Vestibulum posuere nisi in felis sagittis sodales.")
    l.add("Proin lacus augue, dictum ut ipsum pharetra, lacinia pharetra turpis.")
    l.add("Proin non mi nec felis convallis viverra.")
    l.add("Aliquam eu leo pretium, auctor quam vitae, dictum massa.")
    l.add("Cras sagittis purus quis tristique fermentum.")
    l.add("In dolor sapien, ornare quis diam id, laoreet consequat urna.")
    l.add("Aliquam tristique felis non ante vestibulum, in ultricies neque consequat.")
    l.add("Donec convallis mi est, sed commodo nulla dapibus scelerisque.")
    l.add("Suspendisse rhoncus quam ipsum, vel pellentesque eros ullamcorper a.")
    l.add("Maecenas sed semper elit.")
    l.add("Vivamus posuere a mi eget ornare.")
    l.add("Nunc semper consectetur maximus.")
    l.add("Cras facilisis mauris sed metus tincidunt pulvinar.")
    l.add("Suspendisse placerat leo dolor, vitae bibendum risus mattis et.")
    l.add("Phasellus fermentum arcu eu dui efficitur ultricies.")
    l.add("Donec nec eros quis leo maximus porttitor bibendum vel enim.")
    l.add("Donec pharetra libero libero, eget hendrerit justo elementum quis.")
    l.add("In facilisis ante purus, vel scelerisque augue pretium a.")
    l.add("Sed semper, dolor at bibendum porttitor, ipsum risus vestibulum lacus, non cursus lectus eros sit amet sem.")
    l.add("Proin placerat sit amet felis sit amet condimentum.")

    l.shuffle()

    return l
}

