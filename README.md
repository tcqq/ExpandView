[![API](https://img.shields.io/badge/API-17%2B-brightgreen.svg?style=flat)](https://android-arsenal.com/api?level=17)
[![JitPack](https://jitpack.io/v/tcqq/ExpandView.svg)](https://jitpack.io/#tcqq/ExpandView)
[![Licence](https://img.shields.io/badge/Licence-Apache2-blue.svg)](http://www.apache.org/licenses/LICENSE-2.0)

# ExpandView

Widget for showing collapsed or expanded state.

# Usage
Supported attributes with _default_ values:
``` 
<com.tcqq.expandview.ExpandView
    xmlns:app="http://schemas.android.com/apk/res-auto"
    android usual attrs
    (see below).../>
```
|**Attrs**|**Default** |
|:---|:---|
| `expand_expanded` | `false`
| `expand_text_appearance` | `@style/TextAppearance.MaterialComponents.Button`
| `expand_text_color` | `?attr/colorSecondary`
| `expand_icon_color` | `?attr/colorSecondary`
| `expand_more_text` | `@string/see_less`
| `expand_more_icon` | `@drawable/ic_expand_less_black_24dp`
| `expand_less_text` | `@string/see_more`
| `expand_less_icon` | `@drawable/ic_expand_less_black_24dp`

# Setup
#### build.gradle
```
repositories {
    maven { url 'https://jitpack.io' }
}
```
```
dependencies {
    // Using JitPack
    implementation 'com.github.tcqq:expandview:3.1.1'
}
```

# Screenshots

![See more](/screenshots/see_more.png)
![See less](/screenshots/see_less.png)

License
-------

Copyright 2019 Perry Lance

Licensed to the Apache Software Foundation (ASF) under one or more contributor
license agreements.  See the NOTICE file distributed with this work for
additional information regarding copyright ownership.  The ASF licenses this
file to you under the Apache License, Version 2.0 (the "License"); you may not
use this file except in compliance with the License.  You may obtain a copy of
the License at

  http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.  See the
License for the specific language governing permissions and limitations under
the License.
