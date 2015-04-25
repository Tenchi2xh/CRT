varying vec3 normal, lightDir, eyeVec;
varying vec2 texCoord;
varying float dist;

uniform bool isHighlighted;

// uniform float specular;
// uniform float diffuse;

void main(void) {
    normal = gl_NormalMatrix * gl_Normal;
    
    vec3 vVertex = vec3(gl_ModelViewMatrix * gl_Vertex);
    dist = -vVertex.z;
    lightDir = vec3(gl_LightSource[0].position.xyz - vVertex);
    eyeVec = -vVertex;

    gl_Position = ftransform();
    texCoord = gl_MultiTexCoord0.xy;
}