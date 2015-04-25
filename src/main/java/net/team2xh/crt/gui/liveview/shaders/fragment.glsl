varying vec3 normal, lightDir, eyeVec;
varying vec2 texCoord;
varying float dist;

uniform bool isHighlighted;

vec2 phong(float lightInt, float shininess) {
    vec3 s = normalize(lightDir);
    vec3 v = normalize(eyeVec);
    vec3 n = normalize(normal);
    vec3 h = normalize(v+s);
    float diffuse = lightInt * max(0.0, dot(n, s));
    float spec =  pow(max(0.0, dot(n,h)), shininess);
    return vec2(diffuse, spec);
}

vec3 rim(vec3 color, float start, float end, float coef) {
    vec3 normal = normalize(normal);
    vec3 eye = normalize(eyeVec);
    float rim = smoothstep(start, end, 1.0 - dot(normal, eye));
    return clamp(rim, 0.0, 1.0) * coef * color;
}

vec3 stripes(vec3 color) {
    float a = sin((gl_FragCoord.x - gl_FragCoord.y) * 0.3);
    float b = sign(a) / 2 + 1;

    return mix(vec3(0.6), color, b);
}

vec3 stripes2(vec3 color) {
    float a = mod(gl_FragCoord.x - gl_FragCoord.y, 10.0);
    if (a < 3.0)
        return color;
    else
        return color + vec3(0.1);
}

void main (void) {
    //vec4 in_color = texture(tex, gl_FragCoord.xy / tex_size);
    vec2 bp = phong(1.0, 200.0);
    vec3 matcolor = vec3(0.1, 0.3, 0.5);
    vec3 color = matcolor * bp.x + matcolor * bp.y;

    //float t = (100.0 / (dist*dist));

    //vec3 border = rim(vec3(1.0), 0.8, 0.2, 1.0);
    
    if (isHighlighted) {
        //color += border;
        color = stripes2(color);
    }


    gl_FragColor = vec4(color, 1.0);
}